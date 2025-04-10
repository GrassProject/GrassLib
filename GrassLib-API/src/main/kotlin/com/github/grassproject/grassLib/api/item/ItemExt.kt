package com.github.grassproject.grassLib.api.item

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

private val ARMOR_MATERIALS = setOf(
    Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
    Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET,
    Material.GOLDEN_BOOTS, Material.GOLDEN_LEGGINGS, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_HELMET,
    Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET,
    Material.NETHERITE_BOOTS, Material.NETHERITE_LEGGINGS, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_HELMET
)

private val HELMET_MATERIALS = ARMOR_MATERIALS.filter { it.name.endsWith("_HELMET") || it.name.contains("SKULL") }.toSet()
private val CHESTPLATE_MATERIALS = ARMOR_MATERIALS.filter { it.name.endsWith("_CHESTPLATE") || it.name==Material.ELYTRA.name }.toSet()
private val LEGGINGS_MATERIALS = ARMOR_MATERIALS.filter { it.name.endsWith("_LEGGINGS") }.toSet()
private val BOOTS_MATERIALS = ARMOR_MATERIALS.filter { it.name.endsWith("_BOOTS") }.toSet()

private val STUFF_MATERIALS = setOf(
    Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,
    Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE,
    Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
    Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL,
    Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE,
    Material.FISHING_ROD, Material.BOW, Material.CROSSBOW, Material.TRIDENT, Material.FLINT_AND_STEEL, Material.SHEARS, Material.MACE
)

private val SKULL_MATERIALS = setOf(Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD)

fun ItemStack.isEnchantable(): Boolean = type in ARMOR_MATERIALS || type in STUFF_MATERIALS

fun ItemStack.isStuffOrArmor(): Boolean = isEnchantable()

fun ItemStack.isSkull(): Boolean = type in SKULL_MATERIALS

fun ItemStack.isArmor(): Boolean = type in ARMOR_MATERIALS || this.type.equipmentSlot.isArmor

fun ItemStack.isHelmet(): Boolean = type in HELMET_MATERIALS

fun ItemStack.isChestplate(): Boolean = type in CHESTPLATE_MATERIALS

fun ItemStack.isLeggings(): Boolean = type in LEGGINGS_MATERIALS

fun ItemStack.isBoots(): Boolean = type in BOOTS_MATERIALS

fun ItemStack.isStuff(): Boolean = type in STUFF_MATERIALS

fun FileConfiguration.toGrassItem(key: String): GrassItem {
    return ItemUtils.create(
        item = ItemUtils.createItem(getString("$key.material")!!)!!,
        name = getString("$key.name"),
        description = getStringList("$key.lore").toMutableList(),
        amount = getInt("$key.amount", 1),
        modelData = getInt("$key.model-data", -1)
    )
}

/*
fun ConfigurationSection.loadGrassItem(): GrassItem? {
    return ItemUtils.fromSection(this)
}
*/

fun GrassItem.Companion.loadGrassItem(section: ConfigurationSection?): GrassItem? {
    return ItemUtils.fromSection(section)
}

fun GrassItem.toItemStack(): ItemStack {
    return this.getItem()
}

fun ItemStack.toGrassItem(): GrassItem = ItemUtils.create(this)

fun Entity.itemFromPDC(plugin: JavaPlugin, pdc: String): ItemStack? =
    persistentDataContainer.get(NamespacedKey(plugin, pdc), PersistentDataType.STRING)?.let { value ->
        try {
            ItemStack(Material.valueOf(value.uppercase()))
        } catch (e: IllegalArgumentException) {
            null
        }
    }

fun ItemStack.giveToPlayer(player: Player) {
    player.inventory.addItem(this)
}

fun Player.giveItem(item: ItemStack) {
    this.inventory.addItem(item)
}