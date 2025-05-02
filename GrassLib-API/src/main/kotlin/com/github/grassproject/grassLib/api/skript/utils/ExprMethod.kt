package com.github.grassproject.grassLib.api.skript.utils

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.github.grassproject.grassLib.api.reflect.Reflects
import org.bukkit.event.Event

class ExprMethod: SimpleExpression<Any>() {
    init {
        Skript.registerExpression(
            ExprMethod::class.java,
            Any::class.java,
            ExpressionType.SIMPLE,
            "invoke %string% with %strings%"
            // set {_result} to invoke "com.example.Util#compute" with "1", "2"
        )
    }

    private var pattern: Int = 0
    private var methodExpr: Expression<String>?=null
    private var argsExpr: Expression<String>?=null

    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: SkriptParser.ParseResult
    ): Boolean {
        pattern=matchedPattern
        methodExpr=exprs[0] as Expression<String>
        argsExpr=exprs[1] as Expression<String>
        return true
    }

    override fun get(event: Event): Array<Any> {
        try {
            val method = Reflects.getMethod(methodExpr?.getSingle(event).toString()) ?: return arrayOf()
            val args = argsExpr?.getAll(event) ?: emptyArray()
            val value = Reflects.getValue(method, null, *args) ?: return arrayOf()
            return arrayOf(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return arrayOf()
        }
    }


    override fun isSingle() = true
    override fun getReturnType(): Class<out Any> = Any::class.java
    override fun toString(e: Event?, debug: Boolean) = "a"
}