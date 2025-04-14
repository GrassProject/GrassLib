package com.github.grassproject.grassLib.api.utilities.pdc

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.io.*
import java.util.*

fun PersistentDataContainer.setItem(
    namespacedKey: NamespacedKey,
    value: ItemStack
) {
    val byteOutput = ByteArrayOutputStream()
    Bukkit.getItemFactory().let {
        ObjectOutputStream(byteOutput).use { out -> out.writeObject(value) }
    }
    val base64 = Base64.getEncoder().encodeToString(byteOutput.toByteArray())
    this.set(namespacedKey, PersistentDataType.STRING, base64)
}

fun PersistentDataContainer.getItem(key: NamespacedKey): ItemStack? {
    val base64 = this.get(key, PersistentDataType.STRING) ?: return null
    val bytes = Base64.getDecoder().decode(base64)
    val input = ByteArrayInputStream(bytes)
    val obj = ObjectInputStream(input).use { it.readObject() }
    return obj as? ItemStack
}

class PDCManager(private val item: ItemStack) {
    private val meta = item.itemMeta ?: Bukkit.getItemFactory().getItemMeta(item.type)!!

    fun set(key: NamespacedKey, value: Any): PDCManager {
        val container = meta.persistentDataContainer

        when (value) {
            is String -> container.set(key, PersistentDataType.STRING, value)
            is Int -> container.set(key, PersistentDataType.INTEGER, value)
            is Double -> container.set(key, PersistentDataType.DOUBLE, value)
            is Long -> container.set(key, PersistentDataType.LONG, value)
            is Byte -> container.set(key, PersistentDataType.BYTE, value)
            is Short -> container.set(key, PersistentDataType.SHORT, value)
            is Float -> container.set(key, PersistentDataType.FLOAT, value)
            is Boolean -> container.set(key, PersistentDataType.BYTE, if (value) 1 else 0)
            is ItemStack -> container.setItem(key, value)
            else -> error("Unsupported type: ${value::class}")
        }

        item.itemMeta = meta
        return this
    }

    fun <T:Any, Z : Any> get(key: NamespacedKey, type: PersistentDataType<T, Z>): Z? {
        return meta.persistentDataContainer.get(key, type)
    }
    fun has(key: NamespacedKey, type: PersistentDataType<*, *>): Boolean {
        return meta.persistentDataContainer.has(key, type)
    }

    fun remove(key: NamespacedKey): PDCManager {
        meta.persistentDataContainer.remove(key)
        item.itemMeta = meta
        return this
    }

    fun getItem(): ItemStack = item
}
