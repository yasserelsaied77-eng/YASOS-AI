package com.example.core.intelligence.analyzers

import com.example.core.graph.engine.KnowledgeGraphEngine
import com.example.core.graph.model.ExecutiveGraphContext
import com.example.core.graph.model.ExecutiveNode
import com.example.core.graph.model.NodeType
import kotlinx.coroutines.flow.firstOrNull
import java.util.Date

class ContextBuilder(private val graphEngine: KnowledgeGraphEngine) {
    
    suspend fun buildContextForNode(nodeId: String): ExecutiveGraphContext? {
        val allNodes = graphEngine.getAllNodes().firstOrNull() ?: return null
        val rootNode = allNodes.find { it.id == nodeId } ?: return null
        
        val allRelations = graphEngine.getRelationsForNode(nodeId).firstOrNull() ?: emptyList()
        
        val relatedNodes = allRelations.mapNotNull { relation ->
            val otherId = if (relation.fromNodeId == nodeId) relation.toNodeId else relation.fromNodeId
            allNodes.find { it.id == otherId }
        }

        return ExecutiveGraphContext(
            root = rootNode,
            parents = emptyList(), // Can be enriched based on RelationType
            children = emptyList(), // Can be enriched based on RelationType
            related = relatedNodes,
            relations = allRelations
        )
    }

    suspend fun buildSyntheticContext(text: String): ExecutiveGraphContext {
        // Build a synthetic context for analysis before a node is persisted
        val syntheticRoot = ExecutiveNode(
            id = "synthetic_temp_id",
            type = NodeType.INBOX, // Default temp
            title = "Synthetic Context",
            summary = text,
            createdAt = Date().time
        )
        return ExecutiveGraphContext(syntheticRoot, emptyList(), emptyList(), emptyList(), emptyList())
    }
}
