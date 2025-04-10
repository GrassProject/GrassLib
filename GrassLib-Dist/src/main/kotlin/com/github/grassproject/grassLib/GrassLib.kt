package com.github.grassproject.grassLib

import com.github.grassproject.grassLib.api.GrassLibAPI
import org.bukkit.plugin.java.JavaPlugin

class GrassLib : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
            private set
    }

    override fun onEnable() {
        plugin = this
        saveDefaultConfig()
        GrassLibAPI.setupGrassLib(this)
        // DatabaseManager(plugin).init(config) // Example
        // server.pluginManager.registerEvents(TestInventory(), this) // Example
    }
}