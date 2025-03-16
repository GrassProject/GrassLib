package com.github.grassproject.grassLib.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import kotlin.reflect.KProperty

class Config {
    private var file: File
    private var config: FileConfiguration? = null
    private var main: JavaPlugin

/*
    constructor(path: String) {
        main = GrassLib.INSTANCE
        file = File(main.dataFolder, path)
    }

    constructor(file: File) {
        main = GrassLib.INSTANCE
        this.file = file
    }
*/

    constructor(file: File, main: JavaPlugin) {
        this.main = main
        this.file = file
    }

    constructor(path: String, main: JavaPlugin) {
        this.main = main
        file = File(main.dataFolder, path)
    }

    fun load() {
        if (!file.exists()) {
            try {
                main.saveResource(file.name, false)
            } catch (e: IllegalArgumentException) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getConfiguration(): FileConfiguration {
        return config ?: run {
            load()
            config!!
        }
    }

    fun save() {
        try {
            config?.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getFile(): File = file
}

class ConfigDelegate(private val name: String, private val main: JavaPlugin) {

    private val config: Config by lazy {
        Config(name, main).apply { load() }
    }

    operator fun getValue(config: Config, property: KProperty<*>): FileConfiguration {
        return this.config.getConfiguration()
    }

}