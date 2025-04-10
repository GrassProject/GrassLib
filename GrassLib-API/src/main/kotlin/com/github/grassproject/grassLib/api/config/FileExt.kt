package com.github.grassproject.grassLib.api.config

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