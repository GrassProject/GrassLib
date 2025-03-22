package com.github.grassproject.grassLib.utilities

import com.github.grassproject.grassLib.item.ItemUtils.addListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.io.Closeable

object InventoryUtils {
    fun Inventory.onClick(
        plugin: JavaPlugin,
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv=this
        return addListener(plugin, object : Listener {
            @EventHandler
            fun InventoryClickEvent.onClick() {
                if (this.inventory!=inv) return
                listener(whoClicked as? Player ?: return, this)
            }
        })
    }
}