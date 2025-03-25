package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object FileUtil {
    fun getConfigFile(plugin: JavaPlugin, file: String): File = File(plugin.dataFolder, file)

    fun getConfig(file: File): YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun setValue(file: File, path: String, value: Any) {
        getConfig(file).apply {
            set(path, value)
            save(file)
        }
    }

    fun create(plugin: JavaPlugin, path: String): Boolean = create(getConfigFile(plugin, path))

    fun create(file: File): Boolean = prepareFile(file)?.let {
        try {
            it.createNewFile()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    } ?: false

    fun createFromResource(plugin: JavaPlugin, path: String): Boolean =
        createFromResource(plugin, getConfigFile(plugin, path), path)

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

    fun loadYaml(plugin: JavaPlugin, path: String): YamlConfiguration {
        val file = getConfigFile(plugin, path)
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            plugin.saveResource(path, false)
        }
        return getConfig(file)
    }

    private fun prepareFile(file: File): File? = file.takeIf { !it.exists() }?.also {
        it.parentFile?.mkdirs()
    }
}