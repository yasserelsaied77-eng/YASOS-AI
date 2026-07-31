package com.example.core.engine

import com.example.core.graph.engine.KnowledgeGraphEngine
import com.example.core.model.TimelineEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimelineEngine(
    private val graphEngine: KnowledgeGraphEngine
) {
    fun getTimelineEvents(): Flow<List<TimelineEvent>> {
        return graphEngine.getAllNodes().map { nodes ->
            nodes.map { node ->
                TimelineEvent(
                    nodeId = node.id,
                    type = node.type,
                    title = node.title,
                    summary = node.summary,
                    relationCount = 0, // Mock for now, can be enriched via graphEngine later
                    connectedNodes = emptyList(),
                    timestamp = node.createdAt
                )
            }.sortedByDescending { it.timestamp }
        }
    }
}
