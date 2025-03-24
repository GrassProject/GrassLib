package com.github.grassproject.grassLib.utilities

import com.github.grassproject.grassLib.builder.InventoryBuilder
import com.github.grassproject.grassLib.item.ItemUtils
import com.github.grassproject.grassLib.utilities.component.toMiniMessage
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

object InventoryUtils {

    fun loadInventoryFromConfig(config: ConfigurationSection): Inventory {
        return loadInventoryBuilderFromConfig(config).build()
    }

    fun loadInventoryBuilderFromConfig(config: ConfigurationSection): InventoryBuilder {
        val builder = InventoryBuilder()

        config.getString("title")?.let { title ->
            builder.setTitle(title.toMiniMessage())
        }

        val size = config.getInt("size", 9)
        builder.setSize(size)

        config.getString("inventory-type")?.uppercase()?.let { type ->
            val inventoryType = runCatching { InventoryType.valueOf(type) }
                .getOrElse {
                    InventoryType.CHEST
                }
            builder.setType(inventoryType)
        }

        config.getConfigurationSection("contents")?.let { contents ->
            contents.getKeys(false).forEach { key ->
                val section = contents.getConfigurationSection(key) ?: return@forEach

                val itemSection = section.getConfigurationSection("item") ?: return@forEach
                val grassItem = ItemUtils.fromSection(itemSection) ?: return@forEach
                val itemStack = grassItem.getItem()

                // 단일 문자열과 리스트 모두 처리
                val slotsRaw = when {
                    section.isList("slots") -> section.getStringList("slots")
                    section.isString("slots") -> listOf(section.getString("slots")!!)
                    else -> emptyList()
                }

                val slots = slotsRaw.flatMap { slot ->
                    if (slot.contains("..")) {
                        val (start, end) = slot.split("..").map { it.trim().toInt() }
                        (start..end).toList()
                    } else {
                        listOf(slot.toInt())
                    }
                }
                if (slots.isNotEmpty()) {
                    slots.forEach { slot ->
                        builder.setItem(slot, itemStack)
                    }
                }
            }
        }

        return builder
    }
}