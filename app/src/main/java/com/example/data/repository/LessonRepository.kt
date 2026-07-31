package com.example.data.repository

import com.example.data.database.dao.LessonDao
import com.example.data.database.entities.LessonEntity
import kotlinx.coroutines.flow.Flow

class LessonRepository(private val dao: LessonDao) {
    val allLessons: Flow<List<LessonEntity>> = dao.getAllLessons()

    suspend fun insert(entity: LessonEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
