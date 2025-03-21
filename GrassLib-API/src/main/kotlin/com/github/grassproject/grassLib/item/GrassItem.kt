package com.github.grassproject.grassLib.item

import com.github.grassproject.grassLib.utilities.component.toMiniMessage
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class GrassItem(
    private val item: ItemStack,
    val name: String?,
    val description: MutableList<String>?,
    val amount: Int,
    val modelData: Int,
) {

    fun giveItem(player: Player) {
        giveItem(player, amount)
    }

    fun giveItem(player: Player, amount: Int) {
        val item = getItem()
        item.amount = amount

        player.inventory.addItem(item)
    }


    fun getItem(): ItemStack {
        val resultItem = item.clone()
        val itemMeta: ItemMeta = resultItem.itemMeta ?: return resultItem

        name?.let { displayName ->
            itemMeta.displayName("<!i>${displayName}".toMiniMessage())
        }

        description?.let { lore ->
            itemMeta.lore(lore.map { "<!i>${it}".toMiniMessage() })
        }

        modelData.let { itemMeta.setCustomModelData(it) }

        resultItem.itemMeta = itemMeta
        resultItem.amount = amount

        return resultItem
    }

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
