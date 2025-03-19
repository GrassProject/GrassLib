package com.github.grassproject.grassLib.example

import com.github.grassproject.grassLib.builder.InventoryBuilder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class TestInventory : Listener {

    private val customInventory = InventoryBuilder()
        .setType(InventoryType.HOPPER)
        .setTitle("<green>test inventory")
        .build()

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.inventory
        val player = event.whoClicked

        if (inventory == customInventory) {
            if (event.clickedInventory?.type == InventoryType.PLAYER) {
                player.sendMessage("Clicked Player Inventory!")
                event.isCancelled = true
                return
            }
            if (event.slot == -999) return
            // player.sendMessage("Clicked Slot!: ${event.slot}")
            player.sendMessage("Clicked Custom Inventory!")
        }

    }

    @EventHandler
    fun onPlayerJoin(event: org.bukkit.event.player.PlayerJoinEvent) {
        event.player.openInventory(customInventory)
    }
}