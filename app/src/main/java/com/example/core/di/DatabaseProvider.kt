package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.YasosDatabase

object DatabaseProvider {
    @Volatile
    private var INSTANCE: YasosDatabase? = null

    fun getDatabase(context: Context): YasosDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                YasosDatabase::class.java,
                "yasos_database"
            )
            .fallbackToDestructiveMigration()
            .build()
            INSTANCE = instance
            instance
        }
    }
}
