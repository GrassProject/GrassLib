package com.github.grassproject.grassLib.api.config

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration

fun YamlConfiguration.getFloat(path: String, def: Float = 0.0f): Float {
    return try {
        when (val value = get(path)) {
            is Number -> value.toFloat()
            is String -> value.toFloat()
            else -> def
        }
    } catch (e: NumberFormatException) {
        def
    }
}

fun ConfigurationSection.getTriple(path: String): Triple<Float, Float, Float> {
    val raw = getString(path)?.split(",")?.map { it.trim().toFloatOrNull() ?: 0f } ?: return Triple(0f, 0f, 0f)
    return Triple(raw.getOrElse(0) { 0f }, raw.getOrElse(1) { 0f }, raw.getOrElse(2) { 0f })
}

inline fun <reified T : Enum<T>> ConfigurationSection.getEnum(path: String): T? {
    return getString(path)?.uppercase()?.let { value ->
        enumValues<T>().firstOrNull { it.name == value }
    }
}