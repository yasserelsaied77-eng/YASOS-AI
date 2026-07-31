package com.example.data.repository

import com.example.data.database.dao.GraphDao
import com.example.data.database.entities.GraphNodeEntity
import com.example.data.database.entities.GraphRelationEntity
import kotlinx.coroutines.flow.Flow

class GraphRepository(private val dao: GraphDao) {
    val allNodes: Flow<List<GraphNodeEntity>> = dao.getAllNodes()
    val allRelations: Flow<List<GraphRelationEntity>> = dao.getAllRelations()

    suspend fun insertNode(node: GraphNodeEntity) = dao.insertNode(node)
    suspend fun insertRelation(relation: GraphRelationEntity) = dao.insertRelation(relation)

    fun getRelationsForNode(nodeId: String): Flow<List<GraphRelationEntity>> = dao.getRelationsForNode(nodeId)
    suspend fun getNodeById(id: String): GraphNodeEntity? = dao.getNodeById(id)
}
