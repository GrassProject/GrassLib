package com.github.grassproject.grassLib.database

import com.github.grassproject.grassLib.database.impl.MySQLDriver
import com.github.grassproject.grassLib.database.impl.SQLiteDriver
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.sql.Connection

class DatabaseManager(private val plugin: JavaPlugin) {

    private lateinit var dataSource: HikariDataSource
    private lateinit var database: Database

    fun init(config: FileConfiguration) {
        check(!this::dataSource.isInitialized) { "DatabaseManager has already been initialized!" }

        val type = try {
            DatabaseType.valueOf(config.getString("database.type")?.uppercase() ?: "SQLITE")
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Unsupported database type: ${config.getString("database.type")}")
        }

        dataSource = when (type) {
            DatabaseType.SQLITE -> {
                val dbFile = File(plugin.dataFolder, "sqlite.db")
                if (!dbFile.exists()) {
                    dbFile.parentFile.mkdirs()
                    dbFile.createNewFile()
                }
                SQLiteDriver(dbFile)
            }
            DatabaseType.MYSQL -> MySQLDriver(
                host = config.getString("database.credentials.host") ?: "localhost",
                port = config.getInt("database.credentials.port", 3306),
                database = config.getString("database.credentials.database") ?: "database",
                username = config.getString("database.credentials.username") ?: "root",
                password = config.getString("database.credentials.password") ?: "",
                parameters = config.getString("database.credentials.parameters") ?: "",
                maximumPoolSize = config.getInt("database.pool.size", 10),
                poolName = config.getString("database.pool.name") ?: plugin.name
            )
        }

        database = Database.connect(dataSource)
    }

    fun getConnection(): Connection = dataSource.connection

    fun getDatabase(): Database = database

    fun close() {
        if (this::dataSource.isInitialized) {
            dataSource.close()
        }
    }
}

enum class DatabaseType {
    SQLITE, MYSQL
}