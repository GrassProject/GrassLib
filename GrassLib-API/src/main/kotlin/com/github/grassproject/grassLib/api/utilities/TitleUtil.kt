package com.github.grassproject.grassLib.api.utilities

import com.github.grassproject.grassLib.api.builder.TitleBuilder
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@Deprecated("Use TitleBuilder instead")
class TitleUtil(plugin: JavaPlugin, private val key: String, title: String = "", subTitle: String = "") {
    private val titleBuilder = TitleBuilder(plugin, key).apply {
        title(title)
        subTitle(subTitle)
        fadeIn(0L)
        stay(0L)
        fadeOut(0L)
    }

    fun sendToPlayer(player: Player) {
        titleBuilder.sendToPlayer(player)
    }

    fun setTitle(title: String?) {
        titleBuilder.setTitle(title)
    }

    fun setSubTitle(subTitle: String?) {
        titleBuilder.setSubTitle(subTitle)
    }

    fun setFadeIn(seconds: Long) {
        titleBuilder.fadeIn(seconds)
    }

    fun setStay(seconds: Long) {
        titleBuilder.stay(seconds)
    }

    fun setFadeOut(seconds: Long) {
        titleBuilder.fadeOut(seconds)
    }

    fun removeTitle(player: Player) {
        titleBuilder.removeTitle(player)
    }

    val namespacedKey get() = titleBuilder.key
}