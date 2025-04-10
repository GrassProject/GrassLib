package com.github.grassproject.grassLib.api.utilities

import org.bukkit.configuration.ConfigurationSection

inline fun <reified T : Enum<T>> ConfigurationSection.getEnum(path: String): T? {
    return getString(path)?.uppercase()?.let { value ->
        enumValues<T>().firstOrNull { it.name == value }
    }
}