package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object FileUtil {
    fun getConfigFile(plugin: JavaPlugin, file: String): File = File(plugin.dataFolder, file)
    fun getConfig(file: File): YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun setValue(file: File, path: String, value: Any) {
        val config = getConfig(file)
        config.set(path, value)
        config.save(file)
    }

    fun create(plugin: JavaPlugin, path: String): Boolean {
        val file = prepareFile(plugin, path) ?: return false
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun create(file: File): Boolean {
        val preparedFile = prepareFile(file) ?: return false
        return try {
            preparedFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createFromResource(plugin: JavaPlugin, path: String): Boolean {
        val file = prepareFile(plugin, path) ?: return false
        val resource = plugin.getResource(path) ?: return false
        return try {
            resource.use { it.copyTo(file.outputStream()) }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createFromResource(plugin: JavaPlugin, file: File, resourcePath: String): Boolean {
        val preparedFile = prepareFile(file) ?: return false
        val resource = plugin.getResource(resourcePath) ?: return false
        return try {
            resource.use { it.copyTo(preparedFile.outputStream()) }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun prepareFile(plugin: JavaPlugin, path: String): File? {
        val file = getConfigFile(plugin, path)
        if (file.exists()) return null
        val parent = file.parentFile ?: return null
        parent.mkdirs()
        return file
    }

    private fun prepareFile(file: File): File? {
        if (file.exists()) return null
        val parent = file.parentFile ?: return null
        parent.mkdirs()
        return file
    }
}