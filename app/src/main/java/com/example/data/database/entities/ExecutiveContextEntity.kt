package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "executive_context")
data class ExecutiveContextEntity(
    @PrimaryKey
    val id: String,
    val rawInput: String,
    val normalizedInput: String,
    val inputSource: String,
    val inputType: String,
    val intentJson: String,
    val domainJson: String,
    val priority: String,
    val urgency: Int,
    val importance: Int,
    val risk: Int,
    val confidence: Double,
    val requiresDecision: Boolean,
    val requiresExecution: Boolean,
    val requiresKnowledge: Boolean,
    val requiresMemory: Boolean,
    val recommendedEnginesJson: String,
    val strategicAlignment: Int,
    val summary: String,
    val createdAt: Long
)
