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
    poolName: String
) : HikariDataSource(HikariConfig().apply {
    jdbcUrl = buildJdbcUrl(host, port, database, parameters)
    driverClassName = "com.mysql.cj.jdbc.Driver"
    this.username = username
    this.password = password
    connectionTestQuery = "SELECT 1"
    this.maximumPoolSize = maximumPoolSize
    this.poolName = poolName
    addDataSourceProperty("cachePrepStmts", "true")
    addDataSourceProperty("prepStmtCacheSize", "250")
    addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    addDataSourceProperty("useServerPrepStmts", "true")
    addDataSourceProperty("useSSL", "false")
}) {
    companion object {
        fun buildJdbcUrl(host: String, port: Int, database: String, parameters: String): String {
            val baseUrl = "jdbc:mysql://$host:$port/$database"
            return if (parameters.isNotBlank()) "$baseUrl?$parameters" else baseUrl
        }
    }
}