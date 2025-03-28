package com.github.grassproject.grassLibTEST

import com.github.grassproject.grassLib.GrassLibAPI
import com.github.grassproject.grassLib.inventory.InventoryEventHandler
import com.github.grassproject.grassLib.utilities.Register
import com.github.grassproject.grassLibTEST.event.PlayerJoinListener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class GrassLibTEST : JavaPlugin() {
    companion object {
        lateinit var plugin:GrassLibTEST
            private set
    }
    override fun onEnable() {
    plugin=this
        /*
                Register(this).resistEventListener(PlayerJoinListener())
                GrassLibAPI.setup(this)*/


    }
}
