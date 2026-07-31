package com.example.data.repository

import com.example.data.database.dao.DecisionDao
import com.example.data.database.entities.DecisionEntity
import kotlinx.coroutines.flow.Flow

class DecisionRepository(private val dao: DecisionDao) {
    val allDecisions: Flow<List<DecisionEntity>> = dao.getAllDecisions()

    suspend fun insert(entity: DecisionEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
