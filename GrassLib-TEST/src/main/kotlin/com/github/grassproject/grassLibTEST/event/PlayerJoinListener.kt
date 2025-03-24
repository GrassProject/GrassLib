package com.github.grassproject.grassLibTEST.event

import com.github.grassproject.grassLibTEST.inv.InvBuilderTest
import com.github.grassproject.grassLibTEST.inv.TestInv
import jdk.incubator.vector.VectorOperators.Test
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val inv = TestInv() // InvBuilderTest().anvil(event.player)
        inv.open(event.player)
    }
}