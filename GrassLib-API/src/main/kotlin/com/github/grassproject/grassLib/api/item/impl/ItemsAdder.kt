package com.github.grassproject.grassLib.api.item.impl

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.BukkitUtils
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

object ItemsAdder : ItemUtil {
    init {
        if (!BukkitUtils.checkPlugin("ItemsAdder")) {
            throw NotFoundPlugin("ItemsAdder")
        }
    }
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