package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.builder.InventoryBuilder
import com.github.grassproject.grassLib.builder.ItemBuilder
import com.github.grassproject.grassLib.utilities.InventoryUtils.onClick
import com.github.grassproject.grassLibTEST.GrassLibTEST
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class InvBuilderTest {
    fun anvil(player:Player) {
        val inv=InventoryBuilder()
            .setType(InventoryType.ANVIL).setTitle("test anvl gui")
            .build().apply {
                this.setItem(0, ItemBuilder(Material.PAPER).build())
                onClick(GrassLibTEST.plugin) { _, _->
                    println("TEST SUCCESS")
                }
            }
    }
}