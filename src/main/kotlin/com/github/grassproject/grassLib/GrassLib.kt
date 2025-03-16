package com.github.grassproject.grassLib

import org.bukkit.plugin.java.JavaPlugin

class GrassLib : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: GrassLib
            private set
    }

    override fun onEnable() {
        INSTANCE = this
    }
}