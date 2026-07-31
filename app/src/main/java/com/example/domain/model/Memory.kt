package com.example.domain.model

enum class MemoryType {
    DECISION, KNOWLEDGE, LESSON, MEETING, PROJECT, RELATIONSHIP, GOAL, DOCUMENT, IDEA, STRATEGY
}

data class ExtractedEntities(
    val persons: List<String>? = null,
    val organizations: List<String>? = null,
    val locations: List<String>? = null,
    val financialAmounts: List<FinancialAmount>? = null,
    val datesMentioned: List<String>? = null
)

data class FinancialAmount(
    val amount: Double,
    val currency: String
)

data class MemoryMetadata(
    val extractedEntities: ExtractedEntities? = null,
    val tags: List<String>? = null,
    val actionItems: List<String>? = null
)

data class MemoryVaultRecord(
    val id: String,
    val domainCode: DomainCode,
    val memoryType: MemoryType,
    val title: String,
    val content: String,
    val summary: String?,
    val metadata: MemoryMetadata,
    val sourceInboxId: String?,
    val confidenceScore: Double,
    val createdAt: String,
    val updatedAt: String
)
