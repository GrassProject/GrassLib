package com.github.grassproject.grassLib.database.impl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File

class SQLiteDriver(
    databaseFile: File,
): HikariDataSource(HikariConfig().apply {
    jdbcUrl = "jdbc:sqlite:${databaseFile.path}"
    driverClassName = "org.sqlite.JDBC"
    this.connectionTestQuery = "SELECT 1"
    this.maximumPoolSize = 1
})