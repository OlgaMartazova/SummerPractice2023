package com.example.dairy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dairy.data.local.converter.Converters
import com.example.dairy.data.local.preparedata.PrepareData
import com.example.dairy.data.model.Todo

@Database(entities = [Todo::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private lateinit var instance: TodoDatabase

        fun getInstance(context: Context): TodoDatabase {
            if (!::instance.isInitialized) {
                synchronized(TodoDatabase::class) {
                    if (!::instance.isInitialized) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            TodoDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(PrepareData(context))
                            .build()
                    }
                }
            }
            return instance
        }
    }
}