package com.github.grassproject.grassLib.api.item.impl

import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.inventory.ItemStack

object HeadDB : ItemUtil {
    override fun getID(itemStack: ItemStack): String? {
        return HeadDatabaseAPI().getItemID(itemStack)
    }

    override fun getCustomItem(id: String): ItemStack? {
        return HeadDatabaseAPI().getItemHead(id)
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return getID(itemStack) != null
    }
}