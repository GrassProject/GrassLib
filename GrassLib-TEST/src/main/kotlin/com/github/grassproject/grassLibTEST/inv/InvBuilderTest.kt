package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.builder.InventoryBuilder
import com.github.grassproject.grassLib.item.ItemUtils
import com.github.grassproject.grassLib.utilities.InventoryUtils.onItemClick
import com.github.grassproject.grassLibTEST.GrassLibTEST
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class InvBuilderTest {
    fun anvil(player: Player) {
        val inv = InventoryBuilder()
            .setType(InventoryType.ANVIL)
            .setItem(0, ItemStack(Material.PAPER))
            .setItem(1, ItemUtils.createItem("nexo:arm_chair"))
            .build()
            .apply {
                onItemClick(GrassLibTEST.plugin) { p, event ->
                    if (event.clickedInventory?.type == InventoryType.PLAYER) return@onItemClick
                    println("TEST SUCCESS")
                    println("Clicked item: ${event.currentItem?.type ?: "None"}")
                    println("Slot: ${event.slot}")
                    println("Cursor item: ${event.cursor?.type ?: "None"}")
                }
            }

        player.openInventory(inv)
    }
}