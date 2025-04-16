package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.api.inventory.GrassInventory
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

object InvBTest : GrassInventory(builder()
    .setTitle(Component.text("상점").color(NamedTextColor.GREEN))
    .setSize(27)
) {
    override fun initializeItems() {
        inventory.setItem(0, ItemStack(Material.APPLE, 5))
        inventory.setItem(1, ItemStack(Material.GOLD_INGOT, 1))
    }

    override fun onClick(event: InventoryClickEvent) {
        if (event.clickedInventory == inventory) {
            println("a")
        }
        event.whoClicked.sendMessage(Component.text("${event.currentItem?.type.toString()}를 클릭했습니다!"))
        event.isCancelled = true
    }
}