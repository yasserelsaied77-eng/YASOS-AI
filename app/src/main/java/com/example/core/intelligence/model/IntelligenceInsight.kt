package com.example.core.intelligence.model

import com.example.core.graph.model.ExecutiveNode

enum class InsightType {
    PATTERN, CONFLICT, OPPORTUNITY, RECOMMENDATION, DUPLICATE
}

data class IntelligenceInsight(
    val id: String,
    val type: InsightType,
    val title: String,
    val description: String,
    val relatedNodes: List<ExecutiveNode>,
    val confidence: Double,
    val timestamp: Long
)
