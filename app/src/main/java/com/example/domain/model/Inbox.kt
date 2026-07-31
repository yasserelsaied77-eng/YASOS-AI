package com.example.domain.model

enum class InboxSourceType {
    VOICE, TEXT, IMAGE, PDF, URL, BOOK, ARTICLE, EMAIL, MEETING, PROJECT, IDEA, PROBLEM, DECISION, OPPORTUNITY, TASK
}

enum class InboxStatus {
    PENDING, ROUTING, PROCESSED, FAILED, ARCHIVED
}

data class Attachment(
    val url: String,
    val mimeType: String,
    val fileName: String
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

data class InboxRawPayload(
    val sourceClient: String? = null,
    val sender: String? = null,
    val subject: String? = null,
    val attachments: List<Attachment>? = null,
    val location: Location? = null,
    val recordedAt: String? = null
)

data class LifeInboxRecord(
    val id: String,
    val sourceType: InboxSourceType,
    val rawContent: String,
    val rawPayload: InboxRawPayload,
    val status: InboxStatus,
    val errorLog: String?,
    val receivedAt: String,
    val processedAt: String?
)
