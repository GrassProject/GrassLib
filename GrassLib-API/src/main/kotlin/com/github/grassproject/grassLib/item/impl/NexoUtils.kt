package com.github.grassproject.grassLib.item.impl

import com.github.grassproject.grassLib.utilities.PluginUtils
import com.nexomc.nexo.api.NexoItems.exists
import com.nexomc.nexo.api.NexoItems.idFromItem
import com.nexomc.nexo.api.NexoItems.itemFromId
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NexoUtils : ItemUtil {
    init { PluginUtils.checkPlugin("Nexo") }
    override fun getID(itemStack: ItemStack?): String? {
        return itemStack?.let { idFromItem(it) }
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return itemStack?.let { exists(it) } ?: false
    }

    override fun getCustomItem(itemName: String): ItemStack {
        val itemBuilder = itemFromId(itemName)
        return itemBuilder?.build() ?: ItemStack(Material.AIR)
    }
}