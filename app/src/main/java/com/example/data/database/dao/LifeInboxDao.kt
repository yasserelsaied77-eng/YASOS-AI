package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.LifeInboxEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LifeInboxDao {
    @Query("SELECT * FROM life_inbox ORDER BY receivedAt DESC")
    fun getAllInboxItems(): Flow<List<LifeInboxEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LifeInboxEntity)

    @Query("DELETE FROM life_inbox WHERE id = :id")
    suspend fun deleteById(id: String)
}
