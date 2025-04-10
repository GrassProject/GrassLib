package com.github.grassproject.grassLib.api.utilities.component

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

// fun Component.toLegacyString(): String = Component2Str.toString(this)

@Deprecated("Use StringExt.toComponent() instead", ReplaceWith("Component2Str.toString(this)"))
object Component2Str {
    private val COLOR_CODES = mapOf(
        NamedTextColor.BLACK to "§0",
        NamedTextColor.DARK_BLUE to "§1",
        NamedTextColor.DARK_GREEN to "§2",
        NamedTextColor.DARK_AQUA to "§3",
        NamedTextColor.DARK_RED to "§4",
        NamedTextColor.DARK_PURPLE to "§5",
        NamedTextColor.GOLD to "§6",
        NamedTextColor.GRAY to "§7",
        NamedTextColor.DARK_GRAY to "§8",
        NamedTextColor.BLUE to "§9",
        NamedTextColor.GREEN to "§a",
        NamedTextColor.AQUA to "§b",
        NamedTextColor.RED to "§c",
        NamedTextColor.LIGHT_PURPLE to "§d",
        NamedTextColor.YELLOW to "§e",
        NamedTextColor.WHITE to "§f"
    )

    private val DECORATION_CODES = mapOf(
        TextDecoration.BOLD to "§l",
        TextDecoration.ITALIC to "§o",
        TextDecoration.UNDERLINED to "§n",
        TextDecoration.STRIKETHROUGH to "§m",
        TextDecoration.OBFUSCATED to "§k"
    )

    fun toString(component: Component): String {
        val result = StringBuilder()
        processComponent(
            component,
            result,
            NamedTextColor.WHITE,
            Style.empty()
        )
        return result.toString()
    }

    private fun processComponent(component: Component, result: StringBuilder, defaultColor: TextColor, parentStyle: Style) {
        when (component) {
            is TextComponent -> {
                val style = component.style().merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET)
                val color = style.color() ?: defaultColor
                result.append(COLOR_CODES[color] ?: "§f")
                DECORATION_CODES.forEach { (decoration, code) ->
                    if (style.decoration(decoration) == TextDecoration.State.TRUE) result.append(code)
                }
                result.append(component.content())
                component.children().forEach {
                    processComponent(
                        it,
                        result,
                        color,
                        style
                    )
                }
            }
            else -> component.children().forEach {
                processComponent(
                    it,
                    result,
                    defaultColor,
                    parentStyle
                )
            }
        }
    }
}
/*
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
}*/
