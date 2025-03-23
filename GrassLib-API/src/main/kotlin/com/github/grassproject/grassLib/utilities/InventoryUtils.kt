package com.github.grassproject.grassLib.utilities

import com.github.grassproject.grassLib.item.ItemUtils.addListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.Closeable
import java.util.function.Consumer

object InventoryUtils {

    fun interface InventoryClickListener {
        fun onClick(player: Player, event: InventoryClickEvent)
    }

    fun interface InventoryCloseListener {
        fun onClose(player: Player, event: InventoryCloseEvent)
    }

    fun interface InventoryOpenListener {
        fun onOpen(player: Player, event: InventoryOpenEvent)
    }

    fun Inventory.onClick(
        plugin: JavaPlugin,
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onClick(event: InventoryClickEvent) {
                if (event.inventory != inv) return
                val player = event.whoClicked as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.onItemClick(
        plugin: JavaPlugin,
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onItemClick(event: InventoryClickEvent) {
                if (event.inventory != inv) return
                if (event.currentItem == null) return
                val player = event.whoClicked as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.onSpecificClick(
        plugin: JavaPlugin,
        clickType: ClickType,
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onSpecificClick(event: InventoryClickEvent) {
                if (event.inventory != inv) return
                if (event.click != clickType) return
                val player = event.whoClicked as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.onSlotClick(
        plugin: JavaPlugin,
        slot: Int,
        listener: (Player, InventoryClickEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onSlotClick(event: InventoryClickEvent) {
                if (event.inventory != inv) return
                if (event.slot != slot) return
                val player = event.whoClicked as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.onClose(
        plugin: JavaPlugin,
        listener: (Player, InventoryCloseEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onClose(event: InventoryCloseEvent) {
                if (event.inventory != inv) return
                val player = event.player as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.onOpen(
        plugin: JavaPlugin,
        listener: (Player, InventoryOpenEvent) -> Unit
    ): Closeable {
        val inv = this
        val eventListener = object : Listener {
            @EventHandler
            fun onOpen(event: InventoryOpenEvent) {
                if (event.inventory != inv) return
                val player = event.player as? Player ?: return
                listener(player, event)
            }
        }
        return addListener(plugin, eventListener)
    }

    fun Inventory.isEmpty(): Boolean {
        return this.contents.all { it == null || it.type.isAir }
    }

    fun Inventory.hasSpace(item: ItemStack): Boolean {
        val maxStackSize = item.maxStackSize
        for (slot in this.contents) {
            if (slot == null || slot.type.isAir) return true
            if (slot.isSimilar(item) && slot.amount < maxStackSize) return true
        }
        return false
    }

    fun Inventory.addItemSafely(vararg items: ItemStack): Map<Int, ItemStack> {
        val result = mutableMapOf<Int, ItemStack>()
        val remaining = this.addItem(*items)
        remaining.forEach { (slot, item) ->
            if (item != null && !item.type.isAir) {
                result[slot] = item
            }
        }
        return result
    }

    fun Inventory.removeItemSafely(item: ItemStack): Boolean {
        val amountToRemove = item.amount
        var removed = 0
        for (i in 0 until this.size) {
            val slotItem = this.getItem(i) ?: continue
            if (slotItem.isSimilar(item)) {
                val newAmount = slotItem.amount - (amountToRemove - removed)
                if (newAmount <= 0) {
                    this.setItem(i, null)
                    removed += slotItem.amount
                } else {
                    slotItem.amount = newAmount
                    removed = amountToRemove
                }
                if (removed >= amountToRemove) return true
            }
        }
        return removed >= amountToRemove
    }

    fun Inventory.forEachItem(action: Consumer<ItemStack>) {
        this.contents.forEach { if (it != null && !it.type.isAir) action.accept(it) }
    }

    fun Inventory.getViewersAsPlayers(): List<Player> {
        return this.viewers.filterIsInstance<Player>()
    }

    fun Inventory.closeAllViewers() {
        this.getViewersAsPlayers().forEach { it.closeInventory() }
    }
}