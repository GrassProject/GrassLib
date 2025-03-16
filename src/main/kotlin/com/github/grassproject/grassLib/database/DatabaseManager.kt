package com.github.grassproject.grassLib.database

import com.github.grassproject.grassLib.database.impl.MySQLDriver
import com.github.grassproject.grassLib.database.impl.SQLiteDriver
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object DatabaseManager {

    fun init(plugin: JavaPlugin, config: FileConfiguration) {
        val type = config.getString("database.type", "SQLITE")!!.uppercase()

        val host = config.getString("database.credentials.host", "localhost")!!
        val port = config.getInt("database.credentials.port",3306)
        val database = config.getString("database.credentials.database","database")!!
        val username = config.getString("database.credentials.username", "root")!!
        val password = config.getString("database.credentials.password", "")!!
        val parameters = config.getString("database.credentials.parameters", "")!!

        val maximumPoolSize = config.getInt("database.pool.size", 10)
        val poolName = config.getString("database.pool.name", plugin.name)!!

        when (type) {
            "SQLITE" -> {
                val file = File(plugin.dataFolder, "sqlite.db")
                file.createNewFile()
                SQLiteDriver(file)
            }
            "MYSQL" -> {
                MySQLDriver(host, port, database, username, password, parameters, maximumPoolSize, poolName)
            }
            else -> {
                println("Failed to connect to type: $type")
            }
        }
    }

}