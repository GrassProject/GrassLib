package com.github.grassproject.grassLib.hook

import com.github.grassproject.grassLib.exception.NotFoundPlugin
import com.github.grassproject.grassLib.utilities.PluginUtils
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.world.World as WEWorld
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object WorldGuardHook {
    init {
        if (!PluginUtils.checkPlugin("WorldGuard")) {
            throw NotFoundPlugin("WorldGuard")
        }
    }

    fun isInRegion(entity: Entity, region: String): Boolean {
        return getRegion(entity).equals(region, ignoreCase = true)
    }

    fun isInRegion(block: Block, region: String): Boolean {
        return getRegion(block).equals(region, ignoreCase = true)
    }

    fun isInRegion(entity: Entity, regions: Set<String>): Boolean {
        val inRegions = getProtectedRegions(entity.location)
        return inRegions.map { it.id }.any { it in regions }
    }

    fun getRegion(entity: Entity): String {
        return getRegion(entity.location)
    }

    fun getRegion(block: Block): String {
        return getRegion(block.location)
    }

    fun getRegion(location: Location): String {
        return getProtectedRegion(location)?.id ?: ""
    }

    fun getProtectedRegion(entity: Entity): ProtectedRegion? {
        return getProtectedRegion(entity.location)
    }

    fun getProtectedRegions(location: Location): Set<ProtectedRegion> {
        val world = location.world ?: return emptySet()
        val sworld: WEWorld = BukkitAdapter.adapt(world)
        val vector3 = BukkitAdapter.adapt(location).toVector().toBlockPoint()
        val regionManager: RegionManager = WorldGuard.getInstance().platform.regionContainer.get(sworld) ?: return emptySet()
        val set: ApplicableRegionSet = regionManager.getApplicableRegions(vector3)
        return set.regions
    }

    fun getProtectedRegion(location: Location): ProtectedRegion? {
        return getProtectedRegions(location).maxByOrNull { it.priority }
    }

    fun getProtectedRegions(bukkitWorld: World): Collection<ProtectedRegion> {
        val world: WEWorld = BukkitAdapter.adapt(bukkitWorld)
        val regionManager: RegionManager = WorldGuard.getInstance().platform.regionContainer.get(world) ?: return emptySet()
        return regionManager.regions.values
    }

    fun isInRegion(player: Player, regions: List<String>): Boolean {
        val inRegions = getProtectedRegions(player.location)
        return inRegions.any { it.id in regions }
    }

    fun isInRegion(entity: Entity?, regions: List<String>): Boolean {
        val inRegions = getProtectedRegions(entity!!.location)
        return inRegions.any { it.id in regions }
    }

    fun hasExitedRegion(from: Location, to: Location, regions: List<String>): Boolean {
        val wasInRegion = getProtectedRegions(from).any { it.id in regions }
        val isInRegion = getProtectedRegions(to).any { it.id in regions }
        return wasInRegion && !isInRegion
    }

    fun hasExitedRegion(from: Location, to: Location, region: String): Boolean {
        val wasInRegion = getRegion(from).equals(region, ignoreCase = true)
        val isInRegion = getRegion(to).equals(region, ignoreCase = true)
        return wasInRegion && !isInRegion
    }
}