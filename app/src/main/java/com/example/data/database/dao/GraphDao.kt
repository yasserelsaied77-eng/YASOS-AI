package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.GraphNodeEntity
import com.example.data.database.entities.GraphRelationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GraphDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(node: GraphNodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(relation: GraphRelationEntity)

    @Query("SELECT * FROM graph_nodes ORDER BY createdAt DESC")
    fun getAllNodes(): Flow<List<GraphNodeEntity>>

    @Query("SELECT * FROM graph_relations")
    fun getAllRelations(): Flow<List<GraphRelationEntity>>
    
    @Query("SELECT * FROM graph_nodes WHERE id = :id")
    suspend fun getNodeById(id: String): GraphNodeEntity?
    
    @Query("SELECT * FROM graph_relations WHERE fromNodeId = :nodeId OR toNodeId = :nodeId")
    fun getRelationsForNode(nodeId: String): Flow<List<GraphRelationEntity>>
}
