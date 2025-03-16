package com.github.grassproject.grassLib.item

import com.github.grassproject.grassLib.item.impl.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemManager {

    private val utilities: Map<String, ItemUtil> = mapOf(
        "hdb" to HDBUtils,
        "ia" to IAUtils,
        "mythic" to MythicUtils,
        "nexo" to NexoUtils,
        "oraxen" to OraxenUtils
    )

    fun getCreateItem(itemName: String): ItemStack {
        /*val parts = itemName.split(":", limit = 2)
        if (parts.size < 2) return ItemStack(Material.AIR)

        val itemType = parts[0].lowercase()
        val itemValue = parts[1]*/
        val itemType = itemName.split(":", limit = 2)[0]
        val itemValue = itemName.substring(itemName.indexOf(":") + 1)

        return when (itemType) {
            "hdb" -> HDBUtils.getCustomItem(itemValue)
            "ia" -> IAUtils.getCustomItem(itemValue)
            "mythic" -> MythicUtils.getCustomItem(itemValue)
            "nexo" -> NexoUtils.getCustomItem(itemValue)
            "oraxen" -> OraxenUtils.getCustomItem(itemValue)
            else -> ItemStack(Material.AIR)
        }
    }

    fun create(
        item: ItemStack,
        name: String? = null,
        description: MutableList<String>? = null,
        amount: Int = 1,
        modelData: Int = -1
    ): GrassItem {
        return GrassItem(
            item,
            name,
            description,
            amount,
            modelData
        )   
    }

}
