package com.github.grassproject.grassLib

import com.github.grassproject.grassLib.database.DatabaseManager
import org.bukkit.plugin.java.JavaPlugin

class GrassLib : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
            private set
    }

    override fun onEnable() {
        plugin = this
        saveDefaultConfig()

        // DatabaseManager(plugin).init(config)
    }
}