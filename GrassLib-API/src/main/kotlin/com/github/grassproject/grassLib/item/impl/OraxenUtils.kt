package com.github.grassproject.grassLib.item.impl

import com.github.grassproject.grassLib.exception.NotFoundPlugin
import com.github.grassproject.grassLib.utilities.PluginUtils
import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.inventory.ItemStack

object OraxenUtils : ItemUtil {
    init {
        if (!PluginUtils.checkPlugin("Oraxen")) {
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