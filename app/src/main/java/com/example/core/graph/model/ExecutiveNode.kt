package com.example.core.graph.model

data class ExecutiveNode(
    val id: String,
    val type: NodeType,
    val title: String,
    val summary: String,
    val createdAt: Long
)
