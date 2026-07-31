package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val projectId: String?,
    val title: String,
    val description: String,
    val status: String,
    val domain: String,
    val dueDate: Long?,
    val createdAt: Long
)
