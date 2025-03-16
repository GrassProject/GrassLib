package com.github.grassproject.grassLib.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

private val ARMOR_MATERIALS = setOf(
    Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
    Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET,
    Material.GOLDEN_BOOTS, Material.GOLDEN_LEGGINGS, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_HELMET,
    Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET,
    Material.NETHERITE_BOOTS, Material.NETHERITE_LEGGINGS, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_HELMET
)

private val HELMET_MATERIALS = setOf(
    Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET,
    Material.DIAMOND_HELMET, Material.NETHERITE_HELMET
)

private val CHESTPLATE_MATERIALS = setOf(
    Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
    Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE
)

private val LEGGINGS_MATERIALS = setOf(
    Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS,
    Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS
)

private val BOOTS_MATERIALS = setOf(
    Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS,
    Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS
)

private val STUFF_MATERIALS = setOf(
    Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,
    Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE,
    Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
    Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL,
    Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE,
    Material.FISHING_ROD, Material.BOW, Material.CROSSBOW, Material.TRIDENT, Material.FLINT_AND_STEEL, Material.SHEARS, Material.MACE
)

private val SKULL_MATERIALS = setOf(
    Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD
)

fun ItemStack.isEnchantable(): Boolean = isArmor() || isStuff()

fun ItemStack.isStuffOrArmor(): Boolean = isArmor() || isStuff()

fun ItemStack.isSkull(): Boolean = type in SKULL_MATERIALS

fun ItemStack.isArmor(): Boolean = type in ARMOR_MATERIALS

fun ItemStack.isHelmet(): Boolean = type in HELMET_MATERIALS

fun ItemStack.isChestplate(): Boolean = type in CHESTPLATE_MATERIALS

fun ItemStack.isLeggings(): Boolean = type in LEGGINGS_MATERIALS

fun ItemStack.isBoots(): Boolean = type in BOOTS_MATERIALS

fun ItemStack.isStuff(): Boolean = type in STUFF_MATERIALS