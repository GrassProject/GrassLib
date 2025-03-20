package com.github.grassproject.grassLib

import com.github.grassproject.grassLib.events.PlayerChunkChangeEvent
import com.github.grassproject.grassLib.utilities.Register
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class GrassLib : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
            private set
    }

    override fun onEnable() {
        plugin = this
        saveDefaultConfig()
        setupEvents()
        // DatabaseManager(plugin).init(config) // Example
        // server.pluginManager.registerEvents(TestInventory(), this) // Example
    }

    private fun setupEvents() {
        Register(this)
            .resistEventListener(object : Listener {
                private var playerChunk = mutableMapOf<UUID, Chunk?>()
                @EventHandler
                fun PlayerMoveEvent.onMove() {
                    var chunk = playerChunk[player.uniqueId]
                    if (chunk != player.chunk) {
                        Bukkit.getScheduler().runTask(this@GrassLib, Runnable {
                            val event = PlayerChunkChangeEvent(
                                chunk,
                                player.chunk,
                                player
                            );Bukkit.getPluginManager().callEvent(event)
                        })
                        chunk = player.chunk
                        playerChunk[player.uniqueId] = chunk
                    }
                }
            })
    }

}