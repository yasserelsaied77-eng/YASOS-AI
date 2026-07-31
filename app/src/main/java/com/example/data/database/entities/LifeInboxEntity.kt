package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "life_inbox")
data class LifeInboxEntity(
    @PrimaryKey
    val id: String,
    val sourceType: String,
    val rawContent: String,
    val rawPayloadJson: String,
    val status: String,
    val errorLog: String?,
    val receivedAt: String,
    val processedAt: String?
)
