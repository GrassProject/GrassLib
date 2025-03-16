package com.github.grassproject.grassLib.item

import org.bukkit.inventory.ItemStack

class GrassItem(
    private val item: ItemStack,
    val name: String?,
    val description: MutableList<String>?,
    val amount: Int,
    val modelData: Int,
) {
}