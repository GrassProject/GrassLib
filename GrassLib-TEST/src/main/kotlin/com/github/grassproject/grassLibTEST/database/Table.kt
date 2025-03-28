package com.github.grassproject.grassLibTEST.database

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Table : UUIDTable("test", "owner") {
    val name = text("name")
    val count = integer("count").default(0)
}

class Entity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Entity>(Table)

    var name by Table.name
    var count by Table.count

    fun mapping(): DTO = DTO(name = name, count = count)
}