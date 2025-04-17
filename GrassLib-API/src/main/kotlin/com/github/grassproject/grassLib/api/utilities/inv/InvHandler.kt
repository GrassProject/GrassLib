package com.github.grassproject.grassLib.api.utilities.inv

import com.github.grassproject.grassLib.api.GrassAPI
import com.github.grassproject.grassLib.api.item.ItemUtils.addListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import java.io.Closeable

object InvHandler {
    private val plugin=GrassAPI.plugin
    fun Inventory.onClick(
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv=this
        return addListener(plugin, object : Listener {
            @EventHandler
            fun InventoryClickEvent.onClick() {
                if (this.inventory!=inv || this.inventory.holder!=inv.holder) return
                listener(whoClicked as? Player ?: return, this)
            }
        })
    }

    fun Inventory.onClose(
        listener:(Player, InventoryCloseEvent)-> Unit
    ):Closeable {
        val inv=this
        return addListener(plugin, object : Listener {
            @EventHandler
            fun InventoryCloseEvent.onClose() {
                if (this.inventory!=inv || this.inventory.holder!=inv.holder) return
                listener(player as Player, this)
            }
        })
    }

    fun Inventory.onOpen(
        listener:(Player, InventoryOpenEvent)-> Unit
    ):Closeable {
        val inv=this
        return addListener(plugin, object : Listener {
            @EventHandler
            fun InventoryOpenEvent.onOpen() {
                if (this.inventory!=inv || this.inventory.holder!=inv.holder) return
                listener(player as Player, this)
            }
        })
    }
}