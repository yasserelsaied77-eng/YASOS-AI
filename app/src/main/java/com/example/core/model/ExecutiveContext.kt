package com.example.core.model

import java.util.Date

data class ExecutiveContext(
    val id: String,
    val rawInput: String,
    val normalizedInput: String,
    val inputSource: String,
    val inputType: String,
    val intent: List<String>,
    val domain: List<String>,
    val priority: String,
    val urgency: Int,
    val importance: Int,
    val risk: Int,
    val confidence: Double,
    val requiresDecision: Boolean,
    val requiresExecution: Boolean,
    val requiresKnowledge: Boolean,
    val requiresMemory: Boolean,
    val recommendedEngines: List<String>,
    val strategicAlignment: Int,
    val summary: String,
    val createdAt: Date
)
