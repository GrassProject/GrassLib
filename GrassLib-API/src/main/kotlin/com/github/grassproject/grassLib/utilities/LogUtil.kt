package com.github.grassproject.grassLib.utilities

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level
import java.util.logging.Logger

class LogUtil(plugin: JavaPlugin) {
    private val logger: Logger = plugin.logger

    fun info(message: String) {
        logger.info(message)
    }

    fun warning(message: String) {
        logger.warning(message)
    }

    fun severe(message: String) {
        logger.severe(message)
    }

    fun infoColored(message: String) {
        logger.info(colorize(message))
    }

    fun warningColored(message: String) {
        logger.warning(colorize(message))
    }

    fun severeColored(message: String) {
        logger.severe(colorize(message))
    }

    fun log(level: Level, message: String) {
        logger.log(level, message)
    }

    fun logException(message: String, exception: Throwable) {
        logger.log(Level.SEVERE, message, exception)
    }

    private fun colorize(message: String): String {
        return message
            .replace("<red>", "\u001b[31m")
            .replace("<green>", "\u001b[32m")
            .replace("<yellow>", "\u001b[33m")
            .replace("<aqua>", "\u001b[36m")
            .replace("<white>", "\u001b[37m")
            .replace("<reset>", "\u001b[0m")
            .plus("\u001b[0m")
    }
}