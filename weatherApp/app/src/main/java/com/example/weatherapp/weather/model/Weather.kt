package com.example.weatherapp.weather.model

enum class Weather(val code: Int, val urlIcon: String) {
    ClearSky(0, "https://openweathermap.org/img/wn/01d@2x.png"),
    Cloudy(2, "https://openweathermap.org/img/wn/02d@2x.png"),
    Fog(45, "https://openweathermap.org/img/wn/09d@2x.png");

    companion object {
        fun getUrlIconFromCode(code: Int): String =
            values().firstOrNull { code == it.code }?.urlIcon.orEmpty()
    }
}
