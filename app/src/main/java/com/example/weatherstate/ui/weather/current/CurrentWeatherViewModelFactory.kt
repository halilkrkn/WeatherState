package com.example.weatherstate.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstate.data.repository.WeatherStateRepository

class CurrentWeatherViewModelFactory(
        private val weatherStateRepository: WeatherStateRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherStateRepository) as T
    }
}