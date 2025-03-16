package com.github.grassproject.grassLib.database

import com.github.grassproject.grassLib.database.impl.MySQLDriver
import com.github.grassproject.grassLib.database.impl.SQLiteDriver
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import java.io.File

object DatabaseManager {
    private var dataSource: HikariDataSource? = null
    private var exposedDb: Database? = null

    fun init(plugin: JavaPlugin, config: FileConfiguration) {
        val dbSection = config.getConfigurationSection("database")
            ?: throw IllegalStateException("Missing 'database' section in config")
        val type = dbSection.getString("type")?.uppercase() ?: "SQLITE"
        val credentials = dbSection.getConfigurationSection("credentials")?.getValues(false)
            ?: throw IllegalStateException("Missing 'database.credentials' section in config")
        connect(plugin, type, credentials)
    }

    private fun connect(plugin: JavaPlugin, type: String, credentials: Map<String, Any>) {
        dataSource?.close()
        when (type) {
            "MYSQL" -> {
                dataSource = MySQLDriver(
                    host = credentials["host"]?.toString() ?: "localhost",
                    port = credentials["port"]?.toString()?.toIntOrNull() ?: 3306,
                    database = credentials["database"]?.toString() ?: "database",
                    username = credentials["username"]?.toString() ?: "root",
                    password = credentials["password"]?.toString() ?: "",
                    maximumPoolSize = 10,
                    poolName = plugin.name,
                    parameters = credentials["parameters"]?.toString() ?: "?autoReconnect=true"
                )
            }
            "SQLITE" -> {
                val dbFile = File(plugin.dataFolder, "sqlite.db")
                if (!dbFile.parentFile.exists()) {
                    dbFile.parentFile.mkdirs()
                }
                dataSource = SQLiteDriver(dbFile)
            }
            else -> throw IllegalArgumentException("Unsupported database type: $type")
        }
        exposedDb = Database.connect(dataSource!!)
    }

    fun shutdown() {
        dataSource?.close()
        dataSource = null
        exposedDb = null
    }

    fun getDatabase(): Database = exposedDb
        ?: throw IllegalStateException("Database not initialized. Call init() first.")
}