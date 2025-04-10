package com.github.grassproject.grassLib.api.utilities

import com.github.grassproject.grassLib.api.builder.InventoryBuilder
import com.github.grassproject.grassLib.item.ItemUtils
import com.github.grassproject.grassLib.utilities.component.toMiniMessage
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.math.min

object InventoryUtils {

    fun loadInventoryFromConfig(config: ConfigurationSection): Inventory =
        loadInventoryBuilderFromConfig(config).build()

    fun loadInventoryBuilderFromConfig(config: ConfigurationSection): InventoryBuilder = InventoryBuilder()
        .apply {
        config.getString("title")?.let { setTitle(it.toMiniMessage()) }
        setSize(config.getInt("size", 9))

        config.getString("inventory-type")?.uppercase()?.let { type ->
            setType(runCatching { InventoryType.valueOf(type) }.getOrElse { InventoryType.CHEST })
        }

        config.getConfigurationSection("contents")?.let { contents ->
            contents.getKeys(false).forEach { key ->
                val section = contents.getConfigurationSection(key) ?: return@forEach
                val itemSection = section.getConfigurationSection("item") ?: return@forEach
                val grassItem = ItemUtils.fromSection(itemSection) ?: return@forEach
                val itemStack = grassItem.getItem()
                parseSlots(contents, key).forEach { slot -> setItem(slot, itemStack) }
            }
        }
    }

    fun parseSlots(section: ConfigurationSection): List<Int> = parseSlotsRaw(section)

    fun parseSlots(contentsSection: ConfigurationSection, key: String): List<Int> {
        val section = contentsSection.getConfigurationSection(key) ?: return emptyList()
        return parseSlotsRaw(section)
    }

    private fun parseSlotsRaw(section: ConfigurationSection): List<Int> {
        val slotsRaw = when {
            section.isList("slots") -> section.getStringList("slots")
            section.isString("slots") -> listOf(section.getString("slots")!!)
            else -> emptyList()
        }
        return slotsRaw.flatMap { slot ->
            if (".." in slot) {
                val (start, end) = slot.split("..").map { it.trim().toInt() }
                (start..end).toList()
            } else {
                listOf(slot.toInt())
            }
        }
    }

    fun fillSlots(builder: InventoryBuilder, slots: List<Int>, itemStack: ItemStack) {
        slots.forEach { builder.setItem(it, itemStack) }
    }

    fun isValidSlot(inventory: Inventory, slot: Int): Boolean = slot >= 0 && slot < inventory.size

    fun paginateItems(builder: InventoryBuilder, items: List<ItemStack>, page: Int, itemsPerPage: Int) {
        val startIndex = page * itemsPerPage
        val endIndex = min(startIndex + itemsPerPage, items.size)
        items.subList(startIndex, endIndex).forEachIndexed { index, item ->
            builder.setItem(index, item)
        }
    }
}