package com.github.grassproject.grassLib.item

import com.github.grassproject.grassLib.item.impl.*
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.Closeable

object ItemUtils {

    fun createItem(itemName: String): ItemStack? {
        val itemType = itemName.split(":", limit = 2)[0]
        val itemValue = itemName.substring(itemName.indexOf(":") + 1)

        return when (itemType) {
            "ia" -> IAUtils.getCustomItem(itemValue)
            "nexo" -> NexoUtils.getCustomItem(itemValue)
            "oraxen" -> OraxenUtils.getCustomItem(itemValue)
            "mythic" -> MythicUtils.getCustomItem(itemValue)
            "mmo" -> MMOUtils.getCustomItem(itemValue)
            "hdb" -> HDBUtils.getCustomItem(itemValue)
            else -> Material.matchMaterial(itemName.uppercase())?.let { ItemStack(it) } ?: ItemStack(Material.AIR)
        }
    }
/*
    fun getCustomItemId(itemStack: ItemStack): String {
        return when {
            hasNBT(itemStack, "itemsadder") -> "ia:${IAUtils.getID(itemStack)}"
            hasPDC(itemStack, "nexo", "id") -> "nexo:${NexoUtils.getID(itemStack)}"
            MythicUtils.isMythicItem(itemStack) -> "mm:${MythicUtils.getMythicItemID(itemStack)}"
            hasNBT(itemStack, "MMOITEMS_ITEM_TYPE") -> "mmo:${MMOUtils.getNamespaceID(itemStack)}"
            HDBUtils.isCustomItem(itemStack) -> "hdb:${HDBUtils.getID(itemStack)}"
            else -> itemStack.type.toString()
        }
    }

    fun getCustomItemName(itemStack: ItemStack): String {
        if (!hasNBT(itemStack, "itemsadder") && !MythicUtils.isMythicItem(itemStack) && !hasNBT(itemStack, "MMOITEMS_ITEM_TYPE")) {
            if (hasPDC(itemStack, "nexo", "id")) {
                val itemName = NexoUtils.getItemBuilder(itemStack)?.itemName
                if (itemName != null) return PlainTextComponentSerializer.plainText().serialize(itemName) else return "<lang:${itemStack.type.itemTranslationKey}>"
            }

            if (HDBUtils.isCustomItem(itemStack)) {
                val hdbItemName = getItemName(itemStack)
                return PlainTextComponentSerializer.plainText().serialize(hdbItemName)
            }

        } else {
            val itemName = getItemName(itemStack)
            return PlainTextComponentSerializer.plainText().serialize(itemName)
        }

        return "<lang:${itemStack.type.itemTranslationKey}>"
    }

    fun getItemName(itemStack: ItemStack): Component {
        return itemStack.itemMeta.displayName() ?: Component.text("<lang:${itemStack.type.itemTranslationKey}>")
    }

    private fun hasNBT(itemStack: ItemStack?, key: String): Boolean {
        if (itemStack == null || itemStack.type == Material.AIR || !itemStack.hasItemMeta()) return false
        return try {
            val itemNbt: ReadableNBT = NBT.readNbt(itemStack)
            itemNbt.hasTag(key)
        } catch (e: Exception) {
            false
        }
    }*/

    private fun hasPDC(itemStack: ItemStack?, namespace: String, key: String): Boolean {
        if (itemStack == null || !itemStack.hasItemMeta()) return false
        val meta = itemStack.itemMeta
        val PDC = meta.persistentDataContainer
        val namespacedKey = getNamespacedKey(namespace, key)
        return PDC.has(namespacedKey)
    }

    private fun getNamespacedKey(namespace: String, key: String): NamespacedKey = NamespacedKey(namespace, key)

    fun fromSection(
        section: ConfigurationSection?
    ): GrassItem? {
        section ?: return null
        val material = section.getString("material", "STONE")!!
        var lore: MutableList<String>? = null
        if (section.contains("lore")) {
            lore = section.getStringList("lore")
        }
        val flags: MutableList<ItemFlag> = ArrayList()
        if (section.contains("flags")) {
            for (flag in section.getStringList("flags")) {
                val itemFlag = ItemFlag.valueOf(flag.uppercase())
                flags.add(itemFlag)
            }
        }
        return createItem(
            material,
            section.getString("name"),
            lore,
            section.getInt("amount", 1),
            section.getInt("model-data", -1),
            flags
        )
    }

    fun create(
        item: ItemStack,
        name: String? = null,
        description: MutableList<String>? = null,
        amount: Int = 1,
        modelData: Int = -1,
        flags: MutableList<ItemFlag>? = null,
    ): GrassItem {
        return GrassItem(
            item,
            name,
            description,
            amount,
            modelData,
            flags
        )   
    }

    private fun createItem(
        namespace: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modelData: Int,
        flags: MutableList<ItemFlag>?,
    ): GrassItem? {
        val itemStack = createItem(namespace) ?: return null
        return create(
            itemStack,
            name,
            description,
            amount,
            modelData,
            flags
        )
    }

    private fun addListener(plugin: JavaPlugin, listener: Listener): Closeable {
        plugin.server.pluginManager.registerEvents(listener, plugin)
        return Closeable {
            org.bukkit.event.HandlerList.unregisterAll(listener)
        }
    }

    fun ItemStack.onInteraction(
        plugin:JavaPlugin,
        listener: (Player, ItemStack, PlayerInteractEvent) -> Unit
    ): Closeable {
        return addListener(plugin, object : Listener {
            @EventHandler
            fun PlayerInteractEvent.onClick() {
                if (item==null) return
                if (!(item?.isSimilar(this@onInteraction) ?: return)) return
                listener(player, item ?: return, this)
            }
        })
    }
}
