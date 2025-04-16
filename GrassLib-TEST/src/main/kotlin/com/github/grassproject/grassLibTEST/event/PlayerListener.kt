package com.github.grassproject.grassLibTEST.event

import com.github.grassproject.grassLibTEST.inv.InvBTest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class PlayerListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val inv = InvBTest// estInv() // InvBuilderTest().anvil(event.player)
        inv.open(event.player)
    }

    @EventHandler
    fun PlayerMoveEvent.onMove() {
        if (!player.isSprinting) return
        val v = player.location.direction
        val box = player.boundingBox
        val vector = v.normalize().multiply(v.length() * 1.5)
            .apply { y = 0.6 + v.y.coerceAtLeast(0.0) * 0.5 }

        player.location.getNearbyEntities(1.0, 1.0, 1.0).forEach {
            if (it != player && box.overlaps(it.boundingBox)) {
                it.velocity = vector
                (it as? LivingEntity)?.damage(v.length(), player)
            }
        }
    }
}