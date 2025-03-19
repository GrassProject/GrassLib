package com.github.grassproject.grassLib.item.impl

import com.github.grassproject.grassLib.utilities.PluginUtils
import net.Indyuce.mmoitems.MMOItems
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object MMOUtils : ItemUtil {
    private val MMO: MMOItems = MMOItems.plugin

    init  { PluginUtils.checkPlugin("MMOItems") }

    override fun getID(itemStack: ItemStack?): String? {
        return MMOItems.getID(itemStack)
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return getID(itemStack) != null
    }

    override fun getCustomItem(itemName: String): ItemStack {
        val parts = itemName.split(":")
        val type = if (parts.size > 1) parts[0] else null
        val id = if (parts.size > 1) parts[1] else parts[0]

        val types = MMO.types
        if (type == null || !types.has(type)) {
            return ItemStack(Material.AIR)
        }
        val mmoItem = MMO.getMMOItem(types[type], id)
        return mmoItem?.newBuilder()?.build() ?: ItemStack(Material.AIR)
    }

    fun getNamespaceID(itemStack: ItemStack?): String {
        val type = MMOItems.getTypeName(itemStack)
        return "$type:${getID(itemStack)}"
    }
}