package com.example.data.repository

import com.example.data.database.dao.ExecutiveContextDao
import com.example.data.database.entities.ExecutiveContextEntity
import kotlinx.coroutines.flow.Flow

class ExecutiveContextRepository(private val dao: ExecutiveContextDao) {
    val allContexts: Flow<List<ExecutiveContextEntity>> = dao.getAllContexts()

    suspend fun insert(entity: ExecutiveContextEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
