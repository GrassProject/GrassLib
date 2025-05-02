package com.github.grassproject.grassLib.api.hook

import com.github.grassproject.grassLib.api.exception.NotFoundPlugin
import com.github.grassproject.grassLib.api.utilities.BukkitUtils
import org.bukkit.entity.Player
import su.nightexpress.coinsengine.api.CoinsEngineAPI
import su.nightexpress.nightcore.util.NumberUtil

object CoinsEngineHook {

    init {
        if (!BukkitUtils.checkPlugin("CoinsEngine")) {
            throw NotFoundPlugin("CoinsEngine")
        }
    }

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