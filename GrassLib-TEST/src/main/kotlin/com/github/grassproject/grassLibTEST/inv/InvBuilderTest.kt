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
    fun anvil(player:Player) {
        val inv=InventoryBuilder()
            .setType(InventoryType.ANVIL).setTitle("test anvl gui")
            .build().apply {
                this.setItem(0, ItemStack(Material.PAPER))
                this.setItem(1, ItemUtils.createItem("nexo:arm_chair"))
                onItemClick(GrassLibTEST.plugin) { _, event ->
                    if (event.clickedInventory?.type == InventoryType.PLAYER) return@onItemClick
                    println("TEST SUCCESS")
                    println("Clicked item: ${event.currentItem}")
                    println("Cursor item: ${event.slot}")
                }
            }
        player.openInventory(inv)
    }
}