package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.entities.*
import com.example.data.database.dao.*

@Database(
    entities = [
        LifeInboxEntity::class,
        ExecutiveContextEntity::class,
        DecisionEntity::class,
        KnowledgeEntity::class,
        LessonEntity::class,
        StrategyEntity::class,
        ProjectEntity::class,
        TaskEntity::class,
        GraphNodeEntity::class,
        GraphRelationEntity::class,
        IntelligenceInsightEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class YasosDatabase : RoomDatabase() {
    abstract fun lifeInboxDao(): LifeInboxDao
    abstract fun executiveContextDao(): ExecutiveContextDao
    abstract fun decisionDao(): DecisionDao
    abstract fun knowledgeDao(): KnowledgeDao
    abstract fun lessonDao(): LessonDao
    abstract fun strategyDao(): StrategyDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun graphDao(): GraphDao
    abstract fun insightDao(): InsightDao
}
