package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decisions")
data class DecisionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val contextId: String?,
    val description: String,
    val domain: String,
    val createdAt: Long
)
