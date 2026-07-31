package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strategies")
data class StrategyEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val domain: String,
    val targetDate: Long?,
    val createdAt: Long
)
