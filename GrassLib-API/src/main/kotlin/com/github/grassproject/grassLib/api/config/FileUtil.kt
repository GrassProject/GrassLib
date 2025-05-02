package com.github.grassproject.grassLib.api.config

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
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

    fun YamlConfiguration.getFloat(path: String, def: Float = 0.0f): Float {
        return try {
            when (val value = get(path)) {
                is Number -> value.toFloat()
                is String -> value.toFloat()
                else -> def
            }
        } catch (e: NumberFormatException) {
            def
        }
    }

    fun ConfigurationSection.getTriple(path: String): Triple<Float, Float, Float> {
        val raw = getString(path)?.split(",")?.map { it.trim().toFloatOrNull() ?: 0f } ?: return Triple(0f, 0f, 0f)
        return Triple(raw.getOrElse(0) { 0f }, raw.getOrElse(1) { 0f }, raw.getOrElse(2) { 0f })
    }

    inline fun <reified T : Enum<T>> ConfigurationSection.getEnum(path: String): T? {
        return getString(path)?.uppercase()?.let { value ->
            enumValues<T>().firstOrNull { it.name == value }
        }
    }

    fun ConfigurationSection.toFileConfiguration(): FileConfiguration? {
        return this.root as? FileConfiguration
    }

    fun FileConfiguration.toSection(path: String): ConfigurationSection? {
        return this.getConfigurationSection(path)
    }
}