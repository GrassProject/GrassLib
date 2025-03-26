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

fun Player.getEmptySlotCount(): Int {
    return inventory.contents.count { it == null }
}

fun Player.hasItemInSlot(slot: Int): Boolean {
    if (slot !in 0 until inventory.size) return false
    return inventory.getItem(slot) != null
}

fun Player.getEmptySlotIndices(): List<Int> {
    return inventory.contents
        .mapIndexed { index, item -> if (item == null) index else -1 }
        .filter { it != -1 }
}

fun Player.isSlotEmpty(slot: Int): Boolean {
    if (slot !in 0 until inventory.size) return false
    return inventory.getItem(slot) == null
}

fun Player.hasEmptySlots(n: Int): Boolean {
    if (n <= 0 || n > inventory.size) return false
    return inventory.contents.count { it == null } >= n
}

fun Player.hasEmptySlotsInRange(min: Int, max: Int): Boolean {
    if (min <= 0 || max > inventory.size || min > max) return false
    val emptyCount = inventory.contents.count { it == null }
    return emptyCount in min..max
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