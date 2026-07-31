package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "knowledge")
data class KnowledgeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val tagsJson: String,
    val domain: String,
    val createdAt: Long
)
