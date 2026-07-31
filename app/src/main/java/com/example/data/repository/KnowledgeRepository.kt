package com.example.data.repository

import com.example.data.database.dao.KnowledgeDao
import com.example.data.database.entities.KnowledgeEntity
import kotlinx.coroutines.flow.Flow

class KnowledgeRepository(private val dao: KnowledgeDao) {
    val allKnowledge: Flow<List<KnowledgeEntity>> = dao.getAllKnowledge()

    suspend fun insert(entity: KnowledgeEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
