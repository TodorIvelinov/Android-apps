package com.example.weatherapp.service.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class HourlyData(
    @SerializedName("time")
    val listTime: List<String>,
    @SerializedName("temperature_2m")
    val listTemperature: List<Double>,
    @Json(name = "weathercode")
    val weatherCode: Int
)
