package com.github.grassproject.grassLib.api.hook

import com.github.grassproject.grassLib.api.GrassAPI
import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.BukkitUtils
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import com.sk89q.worldedit.world.World as WEWorld

object WorldGuardHook {
    private val regionCache = mutableMapOf<String, List<ProtectedRegion>>()
    private val plugin= GrassAPI.plugin
    init {
        if (!BukkitUtils.checkPlugin("WorldGuard")) {
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

    fun getRegionsInChunkAsync(
        chunk: Chunk,
        callback: (List<ProtectedRegion>) -> Unit
    ) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            val cacheKey = "${chunk.world.name}:${chunk.x}:${chunk.z}"

            regionCache[cacheKey]?.let {
                runOnMainThread { callback(it) }
                return@Runnable
            }

            val regions = computeRegionsInChunk(chunk)

            regionCache[cacheKey] = regions

            runOnMainThread { callback(regions) }
        })
    }

    private fun computeRegionsInChunk(chunk: Chunk): List<ProtectedRegion> {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val world = BukkitAdapter.adapt(chunk.world)

        val minX = chunk.x shl 4
        val minZ = chunk.z shl 4
        val maxX = minX + 15
        val maxZ = minZ + 15
        val minY = chunk.world.minHeight
        val maxY = chunk.world.maxHeight

        val minPoint = BlockVector3.at(minX, minY, minZ)
        val maxPoint = BlockVector3.at(maxX, maxY, maxZ)
        val chunkRegion = ProtectedCuboidRegion("temp_chunk_region", minPoint, maxPoint)

        val regionManager = regionContainer.get(world) ?: return emptyList()
        val applicableRegions = regionManager.getApplicableRegions(chunkRegion)
        return applicableRegions.regions.toList()
    }

    private fun runOnMainThread(action: () -> Unit) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            action()
        })
    }
}