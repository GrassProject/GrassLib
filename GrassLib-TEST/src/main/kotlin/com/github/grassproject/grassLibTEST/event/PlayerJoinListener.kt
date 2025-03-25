package com.github.grassproject.grassLibTEST.event

import com.github.grassproject.grassLib.entity.VirtualEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // val inv = InvBTest// estInv() // InvBuilderTest().anvil(event.player)
        // inv.open(event.player)

        VirtualEntity(
            event.player.location,
        )
    }
}