package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.inventory.GrassInventory
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class TestInv : GrassInventory(27, Component.text("상점").color(NamedTextColor.GREEN)) {
    override fun initializeItems() {
        inventory.setItem(0, ItemStack(Material.APPLE, 5))
        inventory.setItem(1, ItemStack(Material.GOLD_INGOT, 1))
    }

    override fun onClick(event: InventoryClickEvent) {
        if (event.slot == 0) {
            event.whoClicked.sendMessage("사과를 클릭했습니다!")
            event.isCancelled = true
        }
    }

}