package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.api.builder.InventoryBuilder
import com.github.grassproject.grassLib.api.item.ItemUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class InvBuilderTest {
    fun anvil(player: Player) {
        val inv = InventoryBuilder()
            .setType(InventoryType.ANVIL)
            .setItem(0, ItemStack(Material.PAPER))
            .setItem(1, ItemUtils.createItem("nexo:arm_chair")!!)
            .build()

        player.openInventory(inv)
    }
}