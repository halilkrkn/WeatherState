package com.example.weatherstate.ui.weather.Future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModelFactory(
        private val detailDate: LocalDate,
        private val weatherStateRepository: WeatherStateRepository,
        private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(detailDate, weatherStateRepository, unitProvider) as T
    }

}