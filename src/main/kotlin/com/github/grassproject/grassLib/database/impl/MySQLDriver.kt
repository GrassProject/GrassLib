package com.github.grassproject.grassLib.database.impl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class MySQLDriver(
    host: String,
    port: Int,
    database: String,
    username: String,
    password: String,
    parameters: String,
    maximumPoolSize: Int = 10,
    poolName: String,
): HikariDataSource(HikariConfig().apply {
    this.jdbcUrl = "jdbc:mysql://$host:$port/$database$parameters"
    this.driverClassName = "com.mysql.cj.jdbc.Driver"
    this.username = username
    this.password = password
    this.maximumPoolSize = maximumPoolSize
    this.poolName = poolName
    this.addDataSourceProperty("cachePrepStmts", "true")
    this.addDataSourceProperty("prepStmtCacheSize", "250")
    this.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
})