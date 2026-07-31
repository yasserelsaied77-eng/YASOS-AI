package com.example.data.repository

import com.example.data.database.dao.StrategyDao
import com.example.data.database.entities.StrategyEntity
import kotlinx.coroutines.flow.Flow

class StrategyRepository(private val dao: StrategyDao) {
    val allStrategies: Flow<List<StrategyEntity>> = dao.getAllStrategies()

    suspend fun insert(entity: StrategyEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
