package com.example.weatherstate.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository

class CurrentWeatherViewModelFactory(
        private val weatherStateRepository: WeatherStateRepository,
        private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherStateRepository,unitProvider) as T
    }
}