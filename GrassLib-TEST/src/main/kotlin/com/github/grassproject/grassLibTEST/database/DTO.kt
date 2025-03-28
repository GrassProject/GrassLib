package com.github.grassproject.grassLibTEST.database

data class DTO(
    private var name: String,
    private var count: Int,
) {
    var isChanged = false

    fun setCount(count: Int) {
        this.count = count
        isChanged = true
    }

    fun getCount(): Int = count

    fun setName(name: String) {
        this.name = name
        isChanged = true
    }

    fun getName() = name
}
