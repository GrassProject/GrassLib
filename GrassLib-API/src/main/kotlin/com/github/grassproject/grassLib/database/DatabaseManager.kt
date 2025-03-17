package com.github.grassproject.grassLib.database

import com.github.grassproject.grassLib.database.impl.MySQLDriver
import com.github.grassproject.grassLib.database.impl.SQLiteDriver
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.sql.Connection

class DatabaseManager(private val plugin: JavaPlugin) {

    private var dataSource: HikariDataSource? = null

    fun init(config: FileConfiguration) {
        check(dataSource == null) { "DatabaseManager is already initialized!" }

        val type = config.getString("database.type")?.uppercase() ?: "SQLITE"
        dataSource = when (type) {
            "SQLITE" -> SQLiteDriver(File(plugin.dataFolder, "sqlite.db").apply { parentFile.mkdirs(); createNewFile() })
            "MYSQL" -> MySQLDriver(
                host = config.getString("database.credentials.host") ?: "localhost",
                port = config.getInt("database.credentials.port", 3306),
                database = config.getString("database.credentials.database") ?: "database",
                username = config.getString("database.credentials.username") ?: "root",
                password = config.getString("database.credentials.password") ?: "",
                parameters = config.getString("database.credentials.parameters") ?: "",
                maximumPoolSize = config.getInt("database.pool.size", 10),
                poolName = config.getString("database.pool.name") ?: plugin.name
            )
            else -> throw IllegalArgumentException("Unsupported database type: $type")
        }
    }

    fun getConnection(): Connection = dataSource?.connection
        ?: throw IllegalStateException("Database is not initialized!")

    fun close() {
        dataSource?.close()
        dataSource = null
    }
}