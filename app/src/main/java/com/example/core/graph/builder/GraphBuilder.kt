package com.example.core.graph.builder

import com.example.core.graph.engine.KnowledgeGraphEngine
import com.example.core.graph.model.NodeType
import com.example.core.graph.model.RelationType
import com.example.core.intelligence.pipeline.InferenceContext
import com.example.core.model.ExecutiveContext
import com.example.domain.model.LifeInboxRecord
import java.util.Date

class GraphBuilder(private val graphEngine: KnowledgeGraphEngine) {
    
    suspend fun buildInboxNode(inboxRecord: LifeInboxRecord, rawText: String) {
        val inboxTimestamp = try {
            java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US).parse(inboxRecord.receivedAt)?.time ?: Date().time
        } catch (e: Exception) { Date().time }
        
        graphEngine.addNode(
            id = inboxRecord.id,
            type = NodeType.INBOX,
            title = "Captured Input",
            summary = rawText,
            createdAt = inboxTimestamp
        )
    }

    suspend fun buildContextNode(aiContext: ExecutiveContext, inboxId: String) {
        graphEngine.addNode(
            id = aiContext.id,
            type = NodeType.CONTEXT,
            title = "AI Context",
            summary = aiContext.summary,
            createdAt = aiContext.createdAt.time
        )
        graphEngine.addRelation(aiContext.id, inboxId, RelationType.CREATED_FROM)
    }
    
    suspend fun commitInference(inferenceContext: InferenceContext) {
        // In the future, write generated relations, weights, confidence based on the insights
    }
}
