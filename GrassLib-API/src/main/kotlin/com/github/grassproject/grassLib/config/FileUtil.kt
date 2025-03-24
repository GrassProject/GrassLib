package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object FileUtil {
    fun getConfigFile(plugin: JavaPlugin, file: String): File = File(plugin.dataFolder, file)
    fun getConfig(file: File): YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun setValue(file: File, path: String, value: Any) {
        val config= getConfig(file)
        config.set(path, value)
        config.save(file)
    }

    fun create(file: File): Boolean {
        if (file.exists()) return false

        val parent = file.parentFile ?: return false

        parent.mkdirs()
        try {
            return file.createNewFile()
        } catch (exception: IOException) {
            exception.printStackTrace()
            return false
        }
    }

    fun create(plugin: JavaPlugin, file: String): Boolean = create(getConfigFile(plugin, file))
}