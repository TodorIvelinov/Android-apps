package com.example.weatherapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BASE_NAME = "weather_database"

@Database(entities = [WeatherDataEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            val dataBaseInstance = INSTANCE
            if (dataBaseInstance != null) {
                return dataBaseInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
