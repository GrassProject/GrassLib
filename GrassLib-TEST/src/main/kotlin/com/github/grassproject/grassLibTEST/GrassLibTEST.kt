package com.github.grassproject.grassLibTEST

import com.github.grassproject.grassLib.api.GrassAPI
import com.github.grassproject.grassLib.api.utilities.Register
import com.github.grassproject.grassLibTEST.event.PlayerListener
import org.bukkit.plugin.java.JavaPlugin

class GrassLibTEST : JavaPlugin() {
    companion object {
        lateinit var plugin:GrassLibTEST
            private set
    }
    override fun onEnable() {
        plugin = this
        /*
                Register(this).resistEventListener(PlayerJoinListener())
                GrassLibAPI.setup(this)*/
        GrassAPI.setupGrassLib(this)
        Register(this).resistEventListener(PlayerListener())
    }
}
