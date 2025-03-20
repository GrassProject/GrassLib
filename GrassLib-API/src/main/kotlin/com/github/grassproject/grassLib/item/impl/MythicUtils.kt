package com.github.grassproject.grassLib.item.impl

import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.bukkit.adapters.BukkitItemStack
import org.bukkit.inventory.ItemStack

object MythicUtils : ItemUtil {
    override fun getID(itemStack: ItemStack): String? {
        return MythicBukkit.inst().itemManager.getMythicTypeFromItem(itemStack)
    }

    override fun getCustomItem(id: String): ItemStack? {
        return (MythicProvider.get().itemManager.getItem(id).get()
            .generateItemStack(1) as BukkitItemStack).build()
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return MythicBukkit.inst().itemManager.isMythicItem(itemStack)
    }

}