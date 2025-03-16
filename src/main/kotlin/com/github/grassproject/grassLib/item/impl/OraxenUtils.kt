package com.github.grassproject.grassLib.item.impl

import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object OraxenUtils : ItemUtil {
    override fun getID(itemStack: ItemStack?): String? {
        return itemStack?.let { OraxenItems.getIdByItem(it) }
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return itemStack?.let { OraxenItems.exists(it) } ?: false
    }

    override fun getCustomItem(itemName: String): ItemStack {
        val builder = OraxenItems.getItemById(itemName)
        return builder?.build() ?: ItemStack(Material.AIR)
    }
}