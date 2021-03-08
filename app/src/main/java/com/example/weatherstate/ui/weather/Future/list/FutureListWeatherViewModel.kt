package com.example.weatherstate.ui.weather.Future.list

import androidx.lifecycle.ViewModel
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.internal.lazyDeferred
import com.example.weatherstate.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
        private val weatherStateRepository: WeatherStateRepository,
         unitProvider: UnitProvider
) : WeatherViewModel(weatherStateRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        val startDate = LocalDate.now()
        weatherStateRepository.getFutureWeatherList(startDate,super.isMetricUnit)
    }

}