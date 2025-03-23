package com.github.grassproject.grassLibTEST.inv

import com.github.grassproject.grassLib.builder.InventoryBuilder
import com.github.grassproject.grassLib.item.ItemUtils
import com.github.grassproject.grassLib.utilities.InventoryUtils
import com.github.grassproject.grassLib.utilities.InventoryUtils.onClick
import com.github.grassproject.grassLib.utilities.InventoryUtils.onItemClick
import com.github.grassproject.grassLibTEST.GrassLibTEST
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class InvBuilderTest(
    private val plugin: GrassLibTEST
) {
    fun anvil(player:Player) {
        val inv=InventoryBuilder()
            .setType(InventoryType.ANVIL)
            .build().apply {
                this.setItem(0, ItemStack(Material.PAPER))
                this.setItem(1, ItemUtils.createItem("nexo:arm_chair"))
                onClick(plugin, object : InventoryUtils.InventoryClickListener {
                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        event.isCancelled = true
                        if (event.clickedInventory?.type == InventoryType.PLAYER) return

                    }
                })
            }
        player.openInventory(inv)
    }
}