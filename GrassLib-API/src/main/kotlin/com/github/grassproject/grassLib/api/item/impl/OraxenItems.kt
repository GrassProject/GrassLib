package com.github.grassproject.grassLib.api.item.impl

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.BukkitUtils
import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.inventory.ItemStack

object OraxenItems : ItemUtilItems {
    init {
        if (!BukkitUtils.checkPlugin("Oraxen")) {
            throw NotFoundPlugin("Oraxen")
        }
    }
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