package com.github.grassproject.grassLib.api.utilities

import com.github.grassproject.grassLib.api.builder.SoundBuilder
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

object SoundUtils {
    private val lastPlayed = mutableMapOf<Player, Long>()

    fun playSound(
        player: Player,
        sound: Any,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: SoundCategory = SoundCategory.MASTER
    ) {
        val normalizedVolume = normalizeVolume(volume)
        val normalizedPitch = normalizePitch(pitch)
        when (sound) {
            is Sound -> player.playSound(player.location, sound, category, normalizedVolume, normalizedPitch)
            is String -> player.playSound(player.location, sound, category, normalizedVolume, normalizedPitch)
            is List<*> -> playSoundList(
                player,
                sound.filterNotNull(),
                normalizedVolume,
                normalizedPitch,
                category
            )
            is SoundBuilder -> sound.apply { setPlayer(player) }.play()
            else -> throw IllegalArgumentException("Sound는 Sound enum, String, List 또는 SoundBuilder이어야 합니다.")
        }
    }

    fun playSoundAt(
        location: Location,
        sound: Any,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: SoundCategory = SoundCategory.MASTER
    ) {
        val world = location.world ?: throw IllegalArgumentException("Location의 World가 null입니다.")
        val normalizedVolume = normalizeVolume(volume)
        val normalizedPitch = normalizePitch(pitch)
        when (sound) {
            is Sound -> world.playSound(location, sound, category, normalizedVolume, normalizedPitch)
            is String -> world.playSound(location, sound, category, normalizedVolume, normalizedPitch)
            is List<*> -> playSoundListAt(
                location,
                sound.filterNotNull(),
                normalizedVolume,
                normalizedPitch,
                category
            )
            is SoundBuilder -> sound.apply { setLocation(location) }.play()
            else -> throw IllegalArgumentException("Sound는 Sound enum, String, List 또는 SoundBuilder이어야 합니다.")
        }
    }

    fun playSoundForPlayers(
        players: Collection<Player>,
        sound: Any,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: SoundCategory = SoundCategory.MASTER
    ) {
        players.forEach {
            com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(
                it,
                sound,
                volume,
                pitch,
                category
            )
        }
    }

    fun playSoundInRange(
        location: Location,
        radius: Double,
        sound: Any,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: SoundCategory = SoundCategory.MASTER
    ) {
        val world = location.world ?: throw IllegalArgumentException("Location의 World가 null입니다.")
        world.getNearbyEntities(location, radius, radius, radius)
            .filterIsInstance<Player>()
            .forEach {
                com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(
                    it,
                    sound,
                    volume,
                    pitch,
                    category
                )
            }
    }

    fun playSoundFromConfig(
        player: Player,
        config: ConfigurationSection,
        path: String
    ) {
        val (sound, volume, pitch) = com.github.grassproject.grassLib.api.utilities.SoundUtils.parseSoundFromConfig(
            config,
            path
        ) ?: return
        com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(player, sound, volume, pitch)
    }

    fun playSoundWithDebounce(
        player: Player,
        sound: Any,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: SoundCategory = SoundCategory.MASTER,
        debounceMs: Long = 100L
    ) {
        val now = System.currentTimeMillis()
        if (now - (com.github.grassproject.grassLib.api.utilities.SoundUtils.lastPlayed[player] ?: 0L) < debounceMs) return
        com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(player, sound, volume, pitch, category)
        com.github.grassproject.grassLib.api.utilities.SoundUtils.lastPlayed[player] = now
    }

    private fun playSoundList(
        player: Player,
        sounds: List<Any>,
        volume: Float,
        pitch: Float,
        category: SoundCategory,
        random: Boolean = false
    ) {
        if (sounds.isEmpty()) return
        if (random) {
            sounds.randomOrNull()?.let {
                com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(
                    player,
                    it,
                    volume,
                    pitch,
                    category
                )
            }
        } else {
            sounds.forEach {
                com.github.grassproject.grassLib.api.utilities.SoundUtils.playSound(
                    player,
                    it,
                    volume,
                    pitch,
                    category
                )
            }
        }
    }

    private fun playSoundListAt(
        location: Location,
        sounds: List<Any>,
        volume: Float,
        pitch: Float,
        category: SoundCategory,
        random: Boolean = false
    ) {
        if (sounds.isEmpty()) return
        if (random) {
            sounds.randomOrNull()?.let {
                com.github.grassproject.grassLib.api.utilities.SoundUtils.playSoundAt(
                    location,
                    it,
                    volume,
                    pitch,
                    category
                )
            }
        } else {
            sounds.forEach {
                com.github.grassproject.grassLib.api.utilities.SoundUtils.playSoundAt(
                    location,
                    it,
                    volume,
                    pitch,
                    category
                )
            }
        }
    }

    private fun parseSoundFromConfig(
        config: ConfigurationSection,
        path: String
    ): Triple<Any, Float, Float>? {
        val section = config.getConfigurationSection(path) ?: return null
        val sound = section.get("name")?.let {
            when (it) {
                is String -> if (it.isBlank()) null else it
                is List<*> -> it.filterNotNull().filter { item -> item is String && item.toString().isNotBlank() }
                else -> Sound.valueOf(it.toString().uppercase())
            }
        } ?: return null

        if (sound is List<*> && sound.isEmpty()) return null
        val volume = section.getDouble("volume", 1.0).toFloat()
        val pitch = section.getDouble("pitch", 1.0).toFloat()
        return Triple(sound, volume, pitch)
    }

    private fun normalizeVolume(volume: Float): Float = volume.coerceIn(0.0f, 2.0f)

    private fun normalizePitch(pitch: Float): Float = pitch.coerceIn(0.5f, 2.0f)
}