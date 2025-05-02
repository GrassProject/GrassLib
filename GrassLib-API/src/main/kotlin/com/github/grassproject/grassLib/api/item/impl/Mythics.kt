package com.github.grassproject.grassLib.api.item.impl

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.BukkitUtils
import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.bukkit.adapters.BukkitItemStack
import org.bukkit.inventory.ItemStack

object Mythics : ItemUtil {
    init {
        if (!BukkitUtils.checkPlugin("MythicLib")) {
            throw NotFoundPlugin("MythicLib")
        }
    }
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