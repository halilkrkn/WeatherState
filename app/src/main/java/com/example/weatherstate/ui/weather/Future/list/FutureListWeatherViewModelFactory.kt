package com.example.weatherstate.ui.weather.Future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.ui.weather.current.CurrentWeatherViewModel

class FutureListWeatherViewModelFactory(
        private val weatherStateRepository: WeatherStateRepository,
        private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(weatherStateRepository,unitProvider) as T
    }
}