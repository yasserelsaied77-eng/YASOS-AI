package com.example.data.repository

import com.example.data.database.dao.LifeInboxDao
import com.example.data.database.entities.LifeInboxEntity
import kotlinx.coroutines.flow.Flow

class InboxRepository(private val dao: LifeInboxDao) {
    val allInboxItems: Flow<List<LifeInboxEntity>> = dao.getAllInboxItems()

    suspend fun insert(entity: LifeInboxEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
