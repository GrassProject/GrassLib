package com.github.grassproject.grassLib.utilities.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

fun Component.component2str(): String {
    return Component2Str.toString(this)
}

class Component2Str {
    companion object {
        fun toString(component: Component): String {
            val result = StringBuilder()
            processComponent(component, result, NamedTextColor.WHITE, Style.empty())
            return result.toString()
        }

        private fun processComponent(component: Component, result: StringBuilder, defaultColor: TextColor, parentStyle: Style) {
            when (component) {
                is TextComponent -> {
                    val text = component.content()
                    val style = component.style().merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
                    val color = style.color() ?: defaultColor
                    appendColorCode(result, color)
                    appendDecorationCodes(result, style)
                    result.append(text)
                    component.children().forEach { child ->
                        processComponent(child, result, color, style)
                    }
                }
                else -> {
                    component.children().forEach { child ->
                        processComponent(child, result, defaultColor, parentStyle)
                    }
                }
            }
        }

        private fun appendColorCode(result: StringBuilder, color: TextColor) {
            val colorCode = when (color) {
                NamedTextColor.BLACK -> "§0"
                NamedTextColor.DARK_BLUE -> "§1"
                NamedTextColor.DARK_GREEN -> "§2"
                NamedTextColor.DARK_AQUA -> "§3"
                NamedTextColor.DARK_RED -> "§4"
                NamedTextColor.DARK_PURPLE -> "§5"
                NamedTextColor.GOLD -> "§6"
                NamedTextColor.GRAY -> "§7"
                NamedTextColor.DARK_GRAY -> "§8"
                NamedTextColor.BLUE -> "§9"
                NamedTextColor.GREEN -> "§a"
                NamedTextColor.AQUA -> "§b"
                NamedTextColor.RED -> "§c"
                NamedTextColor.LIGHT_PURPLE -> "§d"
                NamedTextColor.YELLOW -> "§e"
                NamedTextColor.WHITE -> "§f"
                else -> "§f"
            }
            result.append(colorCode)
        }

        private fun appendDecorationCodes(result: StringBuilder, style: Style) {
            if (style.decoration(TextDecoration.BOLD) == TextDecoration.State.TRUE) result.append("§l")
            if (style.decoration(TextDecoration.ITALIC) == TextDecoration.State.TRUE) result.append("§o")
            if (style.decoration(TextDecoration.UNDERLINED) == TextDecoration.State.TRUE) result.append("§n")
            if (style.decoration(TextDecoration.STRIKETHROUGH) == TextDecoration.State.TRUE) result.append("§m")
            if (style.decoration(TextDecoration.OBFUSCATED) == TextDecoration.State.TRUE) result.append("§k")
        }
    }
}