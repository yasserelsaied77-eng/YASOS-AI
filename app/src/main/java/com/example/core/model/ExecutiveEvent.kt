package com.example.core.model

enum class ExecutiveEventType {
    INBOX, CONTEXT, DECISION, KNOWLEDGE, PROJECT, TASK, LESSON, STRATEGY
}

data class ExecutiveEvent(
    val id: String,
    val timestamp: Long,
    val type: ExecutiveEventType,
    val source: String,
    val title: String,
    val summary: String,
    val domain: String
)
