package com.example.weatherapp.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.adapter.HourlyTemperatureAdapter
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.repository.WeatherDatabase
import com.example.weatherapp.weather.model.City

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var hourlyTemperatureAdapter: HourlyTemperatureAdapter
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setWeatherDao(WeatherDatabase.getDatabase(requireContext()).weatherDao())
        hourlyTemperatureAdapter = HourlyTemperatureAdapter()
        binding.hourlyTemperatureRecyclerView.adapter = hourlyTemperatureAdapter

        val cityList = listOf(City.SOFIA, City.BURGAS, City.VARNA, City.PLOVDIV)
        val citySpinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, cityList.map { it.name })
        citySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.citySpinner.adapter = citySpinnerAdapter

        binding.fetchButton.setOnClickListener {
            val selectedCity = cityList[binding.citySpinner.selectedItemPosition]
            viewModel.fetchWeatherData(
                latitude = selectedCity.latitude,
                longitude = selectedCity.longitude,
                selectedCity =  selectedCity.name
            )
        }

        weatherIconObserver()
        setObservers()

        return binding.root
    }

    private fun weatherIconObserver() {
        viewModel.weatherIcon.observe(viewLifecycleOwner) { iconUrl ->
            // Use Glide to load the icon into the ImageView
            Glide.with(binding.weatherIconImageView)
                .load(iconUrl)
                .into(binding.weatherIconImageView)
        }
    }

    private fun setObservers() {
        viewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            hourlyTemperatureAdapter.setHourlyTemperatures(weatherData)
        }
    }
}
