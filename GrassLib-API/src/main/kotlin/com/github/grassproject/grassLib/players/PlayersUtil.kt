package com.github.grassproject.grassLib.players

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

fun PlayerInventory.full(): Boolean {
    for (i in 9 until 36) {
        val slot = this.getItem(i)
        if (slot == null || slot.type.isAir || slot.amount < slot.maxStackSize) return false
    }
    return true
}

fun Player.isInventoryFull(): Boolean {
    return inventory.firstEmpty() == -1
}

fun Player.performCommandAsOP(command:String) {
    val isOP=this.isOp
    this.isOp=true
    this.performCommand(command)
    this.isOp=isOP
}

fun Player.takeItem(item: ItemStack, amount: Int): Int {
    require(amount >= 0) { "Amount must be non-negative" }
    if (amount == 0) return 0

    val totalToRemove = minOf(amount, item.amount)
    val inventory = this.inventory
    var removed = 0

    val fullStacks = totalToRemove / 64
    val remainder = totalToRemove % 64

    if (fullStacks > 0) {
        val fullStackItem = item.clone().apply { this.amount = 64 }
        repeat(fullStacks) {
            val result = inventory.removeItem(fullStackItem)
            removed += result.values.sumOf { it.amount }
        }
    }

    if (remainder > 0) {
        val remainderItem = item.clone().apply { this.amount = remainder }
        val result = inventory.removeItem(remainderItem)
        removed += result.values.sumOf { it.amount }
    }

    return removed
}