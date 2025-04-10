package com.github.grassproject.grassLib.api.item.impl

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.utilities.PluginUtils
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

object IAUtils : ItemUtil {
    init {
        if (!PluginUtils.checkPlugin("ItemsAdder")) {
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