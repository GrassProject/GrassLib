package com.github.grassproject.grassLib.item.impl

import org.bukkit.inventory.ItemStack

interface ItemUtil {

    fun getID(itemStack: ItemStack): String?

    fun getCustomItem(id: String): ItemStack?

    fun isCustomItem(itemStack: ItemStack): Boolean
}