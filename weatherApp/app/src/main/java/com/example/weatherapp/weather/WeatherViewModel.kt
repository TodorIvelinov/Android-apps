package com.example.weatherapp.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repository.WeatherDao
import com.example.weatherapp.repository.model.WeatherData
import com.example.weatherapp.service.ApiService
import com.example.weatherapp.service.model.WeatherModel
import com.example.weatherapp.weather.model.Weather
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val CURRENT_TEMPERATURE_FORMAT = "Current Temperature: %s°C"
private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
private const val YEAR_MOUTH_DAY_DATE_FORMAT = "yyyy-MM-dd"
private const val HOUR_MINUTES_FORMAT = "%02d:%02d"

class WeatherViewModel : ViewModel() {

    private val _weatherInfo = MutableLiveData<String>()
    val weatherInfo: LiveData<String> = _weatherInfo

    private val _weatherIcon = MutableLiveData<String>()
    val weatherIcon: LiveData<String> = _weatherIcon

    private val _chosenCity = MutableLiveData<String>()
    val chosenCity: LiveData<String> = _chosenCity

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> = _weatherData

    private lateinit var weatherDao: WeatherDao

    fun setWeatherDao(weatherDao: WeatherDao) {
        this.weatherDao = weatherDao
    }

    fun fetchWeatherData(latitude: Double, longitude: Double, selectedCity: String) {
        _chosenCity.value = selectedCity

        viewModelScope.launch {
            val weatherModel: WeatherModel = ApiService.create().getCityWeather(latitude, longitude)
            val timeList = weatherModel.hourly.listTime
            val temperatureList = weatherModel.hourly.listTemperature

            if (timeList.isNotEmpty() && temperatureList.isNotEmpty()) {
                _weatherIcon.value = Weather.getUrlIconFromCode(weatherModel.hourly.weatherCode)

                val currentDate = SimpleDateFormat(
                    YEAR_MOUTH_DAY_DATE_FORMAT,
                    Locale.getDefault()
                ).format(Date())
                val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                val currentTemperatureIndex = timeList.indexOfFirst {
                    it.startsWith(currentDate) && it.substring(11, 13).toInt() == hourOfDay
                }

                _weatherInfo.value =
                    String.format(
                        CURRENT_TEMPERATURE_FORMAT,
                        temperatureList[currentTemperatureIndex]
                    )

                _weatherData.value = filterDataForCurrentDay(timeList, temperatureList)
            } else {
                // Handle no hourly temperature data
            }
        }
    }

    private fun filterDataForCurrentDay(
        timeList: List<String>,
        temperatureList: List<Double>
    ): List<WeatherData> {
        val currentDate =
            SimpleDateFormat(YEAR_MOUTH_DAY_DATE_FORMAT, Locale.getDefault()).format(Date())

        return (timeList.indices)
            .filter { timeList[it].startsWith(currentDate) }
            .map {
                val formattedDate = convertDataToTime(timeList[it])
                WeatherData(temperatureList[it], formattedDate)
            }
    }

    private fun convertDataToTime(dataString: String): String {
        with(Calendar.getInstance()) {
            time = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).parse(dataString) ?: return ""
            return String.format(
                HOUR_MINUTES_FORMAT,
                get(Calendar.HOUR_OF_DAY),
                get(Calendar.MINUTE)
            )
        }
    }
}
