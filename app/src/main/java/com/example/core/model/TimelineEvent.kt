package com.example.core.model

import com.example.core.graph.model.NodeType

data class TimelineEvent(
    val nodeId: String,
    val type: NodeType,
    val title: String,
    val summary: String,
    val relationCount: Int,
    val connectedNodes: List<String>,
    val timestamp: Long
)
