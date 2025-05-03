package com.github.grassproject.grassLib.api.database.repository

import org.bukkit.entity.Player

interface PlayerRepository<T> {
    fun save(player: Player, value: T)
    fun load(player: Player): T?
    fun loadOrCreate(player: Player): T
}
