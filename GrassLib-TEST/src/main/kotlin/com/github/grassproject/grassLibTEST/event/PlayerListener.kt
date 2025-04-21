package com.github.grassproject.grassLibTEST.event

import com.github.grassproject.grassLib.api.builder.InventoryBuilder
import com.github.grassproject.grassLib.api.utilities.component.toComponent
import com.github.grassproject.grassLib.api.utilities.inv.InvHandler.onClick
import com.github.grassproject.grassLibTEST.inv.InvBTest
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.ItemStack

class PlayerListener : Listener {

//    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val inv = InvBTest// estInv() // InvBuilderTest().anvil(event.player)
        inv.open(event.player)
    }

    @EventHandler
    fun PlayerSwapHandItemsEvent.onEvent() {
        if (!player.isSneaking) return
        val inv=InventoryBuilder()
            .setType(InventoryType.CHEST)
            .setTitle("Custom Chest Inventory".toComponent())
            .setSize(27)
            .modifyInventory { meta->
                meta.setItem(13, ItemStack(Material.PLAYER_HEAD))
            }
            .setItem(13, ItemStack(Material.PLAYER_HEAD))
            .build().apply {
                onClick { p, e->
                    p.sendMessage(e.currentItem.toString())
                }
            }
        player.openInventory(inv)
    }

    @EventHandler
    fun PlayerMoveEvent.onMove() {
        if (!player.isSprinting) return
        val v = player.location.direction
        val box = player.boundingBox
        val vector = v.normalize().multiply(v.length() * 1.5)
            .apply { y = 0.6 + v.y.coerceAtLeast(0.0) * 0.5 }

        player.location.getNearbyEntities(1.0, 1.0, 1.0).forEach {
            if (it != player && box.overlaps(it.boundingBox)) {
                it.velocity = vector
                (it as? LivingEntity)?.damage(v.length(), player)
            }
        }
    }
}