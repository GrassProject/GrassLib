package com.github.grassproject.grassLib.item

import com.github.grassproject.grassLib.utilities.component.str2component
import org.bukkit.inventory.ItemStack

class GrassItem(
    private val item: ItemStack,
    val name: String?,
    val description: MutableList<String>?,
    amount: Int = 1,
    val modelData: Int = 0,
) : ItemStack(item) {
    init {
        val itemMeta = item.itemMeta
        itemMeta.lore(description?.map { it.str2component() } ?: emptyList())
        itemMeta.displayName(name?.str2component())
        itemMeta.setCustomModelData(modelData)
        item.itemMeta = itemMeta
        this.amount = amount
    }
}
