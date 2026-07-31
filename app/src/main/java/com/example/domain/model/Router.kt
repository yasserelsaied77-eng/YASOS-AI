package com.example.domain.model

enum class RouterDestinationTable {
    MEMORY_VAULT, EXECUTION_TASKS, STRATEGY_GOALS, DOMAIN_CUSTOM_RECORDS
}

data class RouterAnalysisResult(
    val targetDomain: DomainCode,
    val destinationTable: RouterDestinationTable,
    val memoryType: MemoryType? = null,
    val suggestedTitle: String,
    val processedContent: String,
    val executiveSummary: String,
    val confidenceScore: Double,
    val aiReasoning: String,
    val metadata: MemoryMetadata,
    val appliedDnaRules: List<InjectedDnaContext>
)

data class RouterAuditLogRecord(
    val id: String,
    val inboxId: String,
    val routedDomain: DomainCode,
    val routedTable: RouterDestinationTable,
    val routedRecordId: String,
    val aiConfidence: Double,
    val aiReasoning: String,
    val createdAt: String
)
