package com.github.grassproject.grassLibTEST

import org.bukkit.plugin.java.JavaPlugin

class GrassLibTEST : JavaPlugin() {
    companion object {
        lateinit var plugin:GrassLibTEST
            private set
    }
    override fun onEnable() {
        plugin=this
    }
}
