package com.github.grassproject.grassLib.item.impl

import net.Indyuce.mmoitems.MMOItems
import org.bukkit.inventory.ItemStack

object MMOUtils : ItemUtil {
    override fun getID(itemStack: ItemStack): String? {
        return MMOItems.getID(itemStack)
    }

    override fun getCustomItem(id: String): ItemStack? {
        val args = id.split(":")
        return MMOItems.plugin.getItem(args[0], args[1])
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return getCustomItem("${getType(itemStack)}:${getID(itemStack)}") != null
    }

    fun getType(itemStack: ItemStack?): String? {
        return MMOItems.getTypeName(itemStack)
    }
}