package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object ConfigManager {
    fun getConfigFile(plugin: JavaPlugin, file:String):File = File(plugin.dataFolder, file)
    fun getConfig(file: File):YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun setValue(file: File, path: String, value: Any) {
        val config= getConfig(file)
        config.set(path, value)
        config.save(file)
    }
}