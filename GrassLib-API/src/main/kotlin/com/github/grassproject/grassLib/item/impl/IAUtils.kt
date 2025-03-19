package com.github.grassproject.grassLib.item.impl

import com.github.grassproject.grassLib.utilities.PluginUtils
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object IAUtils : ItemUtil {
    init { PluginUtils.checkPlugin("ItemsAdder") }
    override fun getID(itemStack: ItemStack?): String? {
        return getCustomStack(itemStack)?.namespacedID
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return getCustomStack(itemStack) != null
    }

    override fun getCustomItem(itemName: String): ItemStack {
        val customStack = CustomStack.getInstance(itemName)
        return customStack?.itemStack ?: ItemStack(Material.AIR)
    }

    private fun getCustomStack(itemStack: ItemStack?): CustomStack? {
        return CustomStack.byItemStack(itemStack)
    }
}