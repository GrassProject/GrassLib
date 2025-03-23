package com.github.grassproject.grassLibTEST.event

import com.github.grassproject.grassLibTEST.GrassLibTEST
import com.github.grassproject.grassLibTEST.inv.InvBuilderTest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val inv = InvBuilderTest(GrassLibTEST.plugin).anvil(event.player)
        println(inv)
    }
}