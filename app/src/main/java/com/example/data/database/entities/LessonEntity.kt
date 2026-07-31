package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val learning: String,
    val sourceContextId: String?,
    val domain: String,
    val createdAt: Long
)
