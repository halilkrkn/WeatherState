package com.example.weatherstate.ui.weather.Future.detail

import androidx.lifecycle.ViewModel
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.internal.lazyDeferred
import com.example.weatherstate.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
        private val detailDate: LocalDate,
        private val weatherStateRepository: WeatherStateRepository,
        unitProvider: UnitProvider
) : WeatherViewModel(weatherStateRepository,unitProvider) {

    val weather by lazyDeferred {
        weatherStateRepository.getDetailFutureWeatherByDate(detailDate,super.isMetricUnit)
    }

}