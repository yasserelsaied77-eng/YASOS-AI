package com.example.core.graph.engine

import com.example.core.graph.model.ExecutiveNode
import com.example.core.graph.model.ExecutiveRelation
import com.example.core.graph.model.NodeType
import com.example.core.graph.model.RelationType
import com.example.data.database.entities.GraphNodeEntity
import com.example.data.database.entities.GraphRelationEntity
import com.example.data.repository.GraphRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class KnowledgeGraphEngine(private val repository: GraphRepository) {

    fun getAllNodes(): Flow<List<ExecutiveNode>> {
        return repository.allNodes.map { entities ->
            entities.map { 
                ExecutiveNode(
                    id = it.id,
                    type = NodeType.valueOf(it.type),
                    title = it.title,
                    summary = it.summary,
                    createdAt = it.createdAt
                )
            }
        }
    }

    fun getRelationsForNode(nodeId: String): Flow<List<ExecutiveRelation>> {
        return repository.getRelationsForNode(nodeId).map { relations ->
            relations.map {
                ExecutiveRelation(
                    id = it.id,
                    fromNodeId = it.fromNodeId,
                    toNodeId = it.toNodeId,
                    relationType = RelationType.valueOf(it.relationType),
                    confidence = it.confidence
                )
            }
        }
    }

    suspend fun addNode(id: String, type: NodeType, title: String, summary: String, createdAt: Long) {
        val node = GraphNodeEntity(
            id = id,
            type = type.name,
            title = title,
            summary = summary,
            createdAt = createdAt
        )
        repository.insertNode(node)
    }

    suspend fun addRelation(
        fromNodeId: String, 
        toNodeId: String, 
        relationType: RelationType, 
        confidence: Double = 1.0
    ) {
        val relation = GraphRelationEntity(
            id = UUID.randomUUID().toString(),
            fromNodeId = fromNodeId,
            toNodeId = toNodeId,
            relationType = relationType.name,
            confidence = confidence
        )
        repository.insertRelation(relation)
    }
}
