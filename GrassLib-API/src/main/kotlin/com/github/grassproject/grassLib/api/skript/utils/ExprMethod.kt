package com.github.grassproject.grassLib.api.skript.utils

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.github.grassproject.grassLib.api.reflect.Reflects
import org.bukkit.Bukkit
import org.bukkit.event.Event

internal var methodMappinng=mutableMapOf<String, String>()
class ExprMethod: SimpleExpression<Any>() {
    companion object {
        init {
            Skript.registerExpression( // line15
                ExprMethod::class.java,
                Any::class.java,
                ExpressionType.SIMPLE,
                "invoke %string% with %strings%"
                // set {_result} to invoke "com.example.Util#compute" with "1", "2"
            )
        }
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
            var name=methodExpr?.getSingle(event).toString()
            if (!methodMappinng[name].isNullOrBlank()) name=methodMappinng[name].toString()
            val method = Reflects.getMethod(name) ?: return arrayOf()
            val args = argsExpr?.getAll(event) ?: emptyArray()
            val value = Reflects.getValue(method, null, *args) ?: return arrayOf()
            return arrayOf(value)
        } catch (e: IllegalArgumentException) {
            Bukkit.getLogger().warning("Invalid signature format. Expected: 'className#methodName'")
            return arrayOf()
        } catch (e: Exception) {
            e.printStackTrace()
            return arrayOf()
        }
    }


    override fun isSingle() = true
    override fun getReturnType(): Class<out Any> = Any::class.java
    override fun toString(e: Event?, debug: Boolean) = "a"
}