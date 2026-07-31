package com.example.core.graph.model

data class ExecutiveRelation(
    val id: String,
    val fromNodeId: String,
    val toNodeId: String,
    val relationType: RelationType,
    val confidence: Double
)
