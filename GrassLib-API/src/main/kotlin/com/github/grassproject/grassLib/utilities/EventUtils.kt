package com.github.grassproject.grassLib.utilities

import org.bukkit.Bukkit
import org.bukkit.event.Cancellable
import org.bukkit.event.Event

object EventUtils {

    fun Event.call(): Boolean {
        Bukkit.getPluginManager().callEvent(this)
        return !(this is Cancellable && this.isCancelled)
    }

    fun <T : Event> T.call(block: T.() -> Unit) {
        Bukkit.getPluginManager().callEvent(this)
        block()
    }
}