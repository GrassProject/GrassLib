package com.github.grassproject.grassLib.item.impl

import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.inventory.ItemStack

object HDBUtils : ItemUtil {
    private val HDB: HeadDatabaseAPI = HeadDatabaseAPI()

    override fun getID(itemStack: ItemStack?): String? {
        return itemStack?.let { HDB.getItemID(it) }
    }

    override fun isCustomItem(itemStack: ItemStack?): Boolean {
        return itemStack?.let { getID(it) != null } ?: false
    }

    override fun getCustomItem(itemName: String): ItemStack {
        return HDB.getItemHead(itemName)
    }
}