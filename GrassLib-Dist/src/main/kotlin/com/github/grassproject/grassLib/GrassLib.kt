package com.github.grassproject.grassLib

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