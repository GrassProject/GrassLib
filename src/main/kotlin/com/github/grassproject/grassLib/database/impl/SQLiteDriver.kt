package com.github.grassproject.grassLib.database.impl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File

class SQLiteDriver(
    databaseFile: File,
): HikariDataSource(HikariConfig().apply {
    this.driverClassName = "org.sqlite.JDBC"
    this.jdbcUrl = "jdbc:sqlite:${databaseFile.path}"
    this.maximumPoolSize = 1
})