package com.example.weatherapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.HourlyTemperatureItemBinding
import com.example.weatherapp.repository.model.WeatherData

class HourlyTemperatureAdapter : RecyclerView.Adapter<HourlyTemperatureAdapter.HourlyTemperatureViewHolder>() {

    private var weatherData: List<WeatherData> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setHourlyTemperatures(weatherData: List<WeatherData>) {
        this.weatherData = weatherData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyTemperatureViewHolder =
        HourlyTemperatureViewHolder(
            HourlyTemperatureItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HourlyTemperatureViewHolder, position: Int) {
        holder.bind(weatherData = weatherData[position])
    }

    override fun getItemCount(): Int = weatherData.size

    inner class HourlyTemperatureViewHolder(private val binding: HourlyTemperatureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherData: WeatherData) {
            binding.weatherData = weatherData
            binding.executePendingBindings()
        }
    }
}
