package com.github.grassproject.grassLib.api.skript.utils

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.event.Event

class EffectMapping: Effect() {
    companion object {
        init {
            Skript.registerEffect(
                EffectMapping::class.java,
                "set (method|reflect) %string% (to|as) %strings%"
            )
        }
    }
    private var methodExpr: Expression<String>?=null
    private var mappingExpr: Expression<String>?=null

    override fun execute(e: Event) {
        val method=methodExpr?.getSingle(e) ?: return
        val mapping=mappingExpr?.getSingle(e) ?: return
        methodMappinng.put(mapping, method)
    }

    override fun toString(p0: Event?, p1: Boolean): String = "add mapping"

    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: SkriptParser.ParseResult
    ): Boolean {
        methodExpr=exprs[0] as Expression<String>
        mappingExpr=exprs[1] as Expression<String>
        return true
    }
}