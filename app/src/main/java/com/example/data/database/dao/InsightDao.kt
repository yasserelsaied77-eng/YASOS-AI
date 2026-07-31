package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.IntelligenceInsightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsight(insight: IntelligenceInsightEntity)

    @Query("SELECT * FROM intelligence_insights ORDER BY timestamp DESC")
    fun getAllInsights(): Flow<List<IntelligenceInsightEntity>>
}
