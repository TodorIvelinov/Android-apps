package com.example.weatherapp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data WHERE latitude = :latitude AND longitude = :longitude")
    fun getWeatherData(latitude: Double, longitude: Double): WeatherDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: WeatherDataEntity)
}
