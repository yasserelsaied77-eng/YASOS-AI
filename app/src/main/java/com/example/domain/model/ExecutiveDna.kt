package com.example.domain.model

enum class DnaCategory {
    VISION,
    VALUE,
    PRINCIPLE,
    CONSTRAINT,
    SUCCESS_RULE,
    FAILURE_RULE,
    DECISION_RULE,
    STRATEGIC_PRIORITY,
    OPERATING_RULE
}

data class DnaContextRules(
    val minFinancialThreshold: Double? = null,
    val triggerKeywords: List<String>? = null,
    val applicableContexts: List<String>? = null
)

data class ExecutiveDnaRecord(
    val id: String,
    val domainCode: DomainCode,
    val category: DnaCategory,
    val title: String,
    val statement: String,
    val contextRules: DnaContextRules,
    val priorityLevel: Int, // 1 to 10
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class InjectedDnaContext(
    val ruleId: String,
    val domainCode: DomainCode,
    val category: DnaCategory,
    val statement: String,
    val priorityLevel: Int,
    val similarityScore: Double
)
