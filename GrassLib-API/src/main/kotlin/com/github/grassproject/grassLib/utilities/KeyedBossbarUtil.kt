package com.github.grassproject.grassLib.utilities

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.KeyedBossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


fun JavaPlugin.createBossBar(key: String, title: String, color: BarColor, style: BarStyle): KeyedBossBar{
    val namespacedKey = NamespacedKey(this, key)
    Bukkit.getBossBar(namespacedKey)?.let { return it }

    return Bukkit.getBossBar(namespacedKey) ?: Bukkit.createBossBar(namespacedKey, title, color, style)
}

fun KeyedBossBar.removeBossBar(){
    Bukkit.removeBossBar(this.key)
}

fun KeyedBossBar.setProgressProbability(progress: Double){
    this.progress = progress.coerceIn(0.0, 1.0)
}

fun KeyedBossBar.setProgressPercentage(progress: Double){
    this.progress = (progress.coerceIn(0.0, 100.0)/100.0)
}

fun KeyedBossBar.getProgressProbability(): Double{
    return this.progress
}

fun KeyedBossBar.getProgressPercentage(): Int{
    return (this.progress * 100).toInt()
}

fun KeyedBossBar.updateStyle(style: BarStyle){
    this.style = style
}

fun KeyedBossBar.updateColor(color: BarColor){
    this.color = color
}

fun KeyedBossBar.updateTitle(title: String){
    this.setTitle(title)
}

