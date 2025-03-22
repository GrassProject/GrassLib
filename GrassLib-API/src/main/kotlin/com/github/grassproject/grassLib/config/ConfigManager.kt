package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object ConfigManager {
    fun getConfigFile(plugin: JavaPlugin, file: String): File = File(plugin.dataFolder, file)
    fun getConfig(file: File): YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun setValue(file: File, path: String, value: Any) {
        val config= getConfig(file)
        config.set(path, value)
        config.save(file)
    }

    fun createFile(plugin: JavaPlugin, file: String): File {
        val configFile = getConfigFile(plugin, file)
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            try {
                configFile.createNewFile()
            } catch (e: IOException) {
                plugin.logger.severe("Failed to create config file: ${configFile.path}")
            }
        }
        return configFile
    }

}