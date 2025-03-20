package com.github.grassproject.grassLib.item.impl

import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.inventory.ItemStack

object OraxenUtils : ItemUtil {
    override fun getID(itemStack: ItemStack): String? {
        return OraxenItems.getIdByItem(itemStack)
    }

    override fun getCustomItem(id: String): ItemStack? {
        return OraxenItems.getItemById(id).build()
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return OraxenItems.exists(itemStack)
    }
}