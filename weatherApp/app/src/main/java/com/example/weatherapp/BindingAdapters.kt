package com.example.weatherapp

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.Locale

private const val TEMPERATURE_FORMAT = "%.1f°C"

@BindingAdapter("temperatureText")
fun TextView.setTemperatureText(temperature: Double) {
    text = String.format(Locale.getDefault(), TEMPERATURE_FORMAT, temperature)
}

@BindingAdapter("visibleGone")
fun View.setViewOrGone(boolean: Boolean?) {
    visibility = if (boolean == true) View.VISIBLE else View.GONE
}
