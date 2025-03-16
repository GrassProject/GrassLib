package com.github.grassproject.lib.util

import org.bukkit.Bukkit

object PluginUtils {
    /*
    private fun getPluginStatus(@NotNull pluginName: String): Plugin? {
        return Bukkit.getPluginManager().getPlugin(pluginName)
    }

    fun isInstalled(@NotNull pluginName: String): Boolean {
        return getPluginStatus(pluginName) != null
    }

    fun isLoaded(@NotNull pluginName: String): Boolean {
        val plugin = getPluginStatus(pluginName)
        return plugin != null && plugin.isEnabled
    }*/

    fun isEnabled(pluginName: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
    }
}