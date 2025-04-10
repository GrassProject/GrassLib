package com.github.grassproject.grassLib.api.item.impl

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.PluginUtils
import com.nexomc.nexo.api.NexoItems
import com.nexomc.nexo.api.NexoItems.builderFromItem
import com.nexomc.nexo.api.NexoItems.exists
import com.nexomc.nexo.api.NexoItems.idFromItem
import com.nexomc.nexo.items.ItemBuilder
import org.bukkit.inventory.ItemStack

object NexoUtils : ItemUtil {
    init {
        if (!PluginUtils.checkPlugin("Nexo")) {
            throw NotFoundPlugin("Nexo")
        }
    }
    override fun getID(itemStack: ItemStack): String? {
        return idFromItem(itemStack)
    }

    override fun getCustomItem(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }

    override fun isCustomItem(itemStack: ItemStack): Boolean {
        return exists(itemStack)
    }

    fun getItemBuilder(itemStack: ItemStack?): ItemBuilder? {
        return builderFromItem(itemStack)
    }

}