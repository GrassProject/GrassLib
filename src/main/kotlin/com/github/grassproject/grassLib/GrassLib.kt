package com.github.grassproject.grassLib

import org.bukkit.plugin.java.JavaPlugin

class GrassLib : JavaPlugin() {
    companion object {
        lateinit var plugin: GrassLib
            private set
    }

    override fun onEnable() {
        plugin = this
        saveDefaultConfig()
    }
}