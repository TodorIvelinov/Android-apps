package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDatabaseDao: TaskDatabaseDao

    companion object {

        private const val DATABASE_NAME = "task_history_database"

        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                }
                return requireNotNull(INSTANCE)
            }
        }
    }
}
