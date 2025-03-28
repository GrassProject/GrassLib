package com.github.grassproject.grassLibTEST.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DataManager(private val database: Database) {

    // DTO를 데이터베이스에 저장 (새 엔티티 생성)
    fun save(dto: DTO): Entity {
        return transaction(database) {
            val entity = Entity.new {
                name = dto.getName()
                count = dto.getCount()
            }
            dto.isChanged = false // 저장 후 변경 플래그 초기화
            entity
        }
    }

    // 기존 엔티티 업데이트
    fun update(id: UUID, dto: DTO) {
        transaction(database) {
            val entity = Entity.findById(id)
            if (entity != null && dto.isChanged) {
                entity.name = dto.getName()
                entity.count = dto.getCount()
                dto.isChanged = false // 업데이트 후 변경 플래그 초기화
            }
        }
    }

    // ID로 DTO 조회
    fun findById(id: UUID): DTO? {
        return transaction(database) {
            Entity.findById(id)?.mapping()
        }
    }

    // 모든 DTO 조회
    fun findAll(): List<DTO> {
        return transaction(database) {
            Entity.all().map { it.mapping() }
        }
    }
}