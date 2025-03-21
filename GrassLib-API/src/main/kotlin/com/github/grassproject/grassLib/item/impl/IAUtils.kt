package com.github.grassproject.grassLib.item.impl

import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

object IAUtils : ItemUtil {
    override fun getID(itemStack: ItemStack): String? {
        return CustomStack.byItemStack(itemStack)?.namespacedID
    }

    override fun getCustomItem(id: String): ItemStack? {
        return CustomStack.getInstance(id)!!.itemStack
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return CustomStack.byItemStack(itemStack) != null
    }
}