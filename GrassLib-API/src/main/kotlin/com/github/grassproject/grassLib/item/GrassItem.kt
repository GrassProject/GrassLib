package com.github.grassproject.grassLib.item

import com.github.grassproject.grassLib.utilities.component.toMiniMessage
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class GrassItem(
    private val item: ItemStack,
    val name: String?,
    val description: MutableList<String>?,
    val amount: Int,
    val modelData: Int,
    val flags: MutableList<ItemFlag>?,
) {

    fun giveItem(player: Player) {
        giveItem(player, amount)
    }

    fun giveItem(player: Player, amount: Int) {
        val item = getItem()
        item.amount = amount

        player.inventory.addItem(item)
    }

    fun getUnmodifiedItem(): ItemStack {
        return item
    }

    fun getItem(): ItemStack {
        val resultItem = getUnmodifiedItem()
        val itemMeta: ItemMeta = resultItem.itemMeta ?: return resultItem

        name?.let { displayName ->
            itemMeta.displayName(displayName.toMiniMessage()) // "<!i>${displayName}".toMiniMessage()
        }

        description?.let { lore ->
            itemMeta.lore(lore.map { it.toMiniMessage() }) // <!i>${it}".toMiniMessage()
        }

        if (modelData > 0) {
            itemMeta.setCustomModelData(modelData)
        }

        flags?.apply {
            itemMeta.addItemFlags(*this.toTypedArray())
        }

        resultItem.amount = amount

        resultItem.itemMeta = itemMeta

        return resultItem
    }

    companion object {}
}


/*: ItemStack(item) {
    init {
        val itemMeta = item.itemMeta
        itemMeta.lore(description?.map { it.str2component() } ?: emptyList())
        itemMeta.displayName(name?.str2component())
        itemMeta.setCustomModelData(modelData)
        item.itemMeta = itemMeta
        this.amount = amount
    }
}*/
