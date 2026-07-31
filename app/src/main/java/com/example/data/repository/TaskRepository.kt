package com.example.data.repository

import com.example.data.database.dao.TaskDao
import com.example.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = dao.getAllTasks()

    suspend fun insert(entity: TaskEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
