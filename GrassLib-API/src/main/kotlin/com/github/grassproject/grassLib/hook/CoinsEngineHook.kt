package com.github.grassproject.grassLib.hook

import org.bukkit.entity.Player
import su.nightexpress.coinsengine.api.CoinsEngineAPI
import su.nightexpress.nightcore.util.NumberUtil

object CoinsEngineHook {

    fun isValidCurrency(name: String): Boolean {
        return CoinsEngineAPI.getCurrency(name) != null
    }

    fun getBalance(player: Player, currency: String): Double {
        return CoinsEngineAPI.getBalance(player.uniqueId, currency)
    }

    fun withdraw(player: Player, currency: String, amount: Double): Boolean {
        return CoinsEngineAPI.removeBalance(player.uniqueId, currency, amount)
    }

    fun format(currencyId: String, amount: Double): String {
        val currency = CoinsEngineAPI.getCurrency(currencyId)
        return currency?.format(amount) ?: NumberUtil.format(amount)
    }
}