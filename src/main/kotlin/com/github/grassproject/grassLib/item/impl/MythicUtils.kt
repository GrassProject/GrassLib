package com.github.grassproject.grassLib.item.impl

import io.lumine.mythic.bukkit.BukkitAdapter
import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.core.items.ItemExecutor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object MythicUtils : ItemUtil {
    private val MMBukkit: MythicBukkit = MythicBukkit.inst()
    private val itemManager: ItemExecutor = MMBukkit.itemManager

    override fun getID(itemStack: ItemStack?): String? {
        return itemManager.getMythicTypeFromItem(itemStack)
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return itemManager.isMythicItem(itemStack)
    }

    override fun getCustomItem(itemName: String): ItemStack {
        val mythicItem = itemManager.getItem(itemName)
        return mythicItem.map { BukkitAdapter.adapt(it.generateItemStack(1)) }
            .orElse(ItemStack(Material.AIR)) as ItemStack
    }
}