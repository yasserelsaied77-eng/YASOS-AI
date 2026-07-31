package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.ExecutiveContextEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExecutiveContextDao {
    @Query("SELECT * FROM executive_context ORDER BY createdAt DESC")
    fun getAllContexts(): Flow<List<ExecutiveContextEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ExecutiveContextEntity)

    @Query("DELETE FROM executive_context WHERE id = :id")
    suspend fun deleteById(id: String)
}
