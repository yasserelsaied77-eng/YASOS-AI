package com.example.data.repository

import com.example.data.database.dao.InsightDao
import com.example.data.database.entities.IntelligenceInsightEntity
import kotlinx.coroutines.flow.Flow

class InsightRepository(private val dao: InsightDao) {
    val allInsights: Flow<List<IntelligenceInsightEntity>> = dao.getAllInsights()

    suspend fun insertInsight(insight: IntelligenceInsightEntity) {
        dao.insertInsight(insight)
    }
}
