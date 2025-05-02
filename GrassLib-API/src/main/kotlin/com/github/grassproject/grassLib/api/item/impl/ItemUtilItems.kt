package com.github.grassproject.grassLib.api.item.impl

import org.bukkit.inventory.ItemStack

interface ItemUtilItems {

    fun getID(itemStack: ItemStack): String?

    fun getCustomItem(id: String): ItemStack?

    fun isCustomItem(itemStack: ItemStack): Boolean
}