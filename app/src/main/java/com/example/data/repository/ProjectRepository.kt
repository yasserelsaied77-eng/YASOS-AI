package com.example.data.repository

import com.example.data.database.dao.ProjectDao
import com.example.data.database.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val dao: ProjectDao) {
    val allProjects: Flow<List<ProjectEntity>> = dao.getAllProjects()

    suspend fun insert(entity: ProjectEntity) = dao.insert(entity)

    suspend fun deleteById(id: String) = dao.deleteById(id)
}
