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

    fun createFromResource(plugin: JavaPlugin, path: String): Boolean {
        val file = getConfigFile(plugin, path)
        if (file.exists()) return false

        val parent = file.parentFile ?: return false
        parent.mkdirs()

        val resource = plugin.getResource(path) ?: return false
        return try {
            resource.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (exception: IOException) {
            exception.printStackTrace()
            false
        }
    }
}