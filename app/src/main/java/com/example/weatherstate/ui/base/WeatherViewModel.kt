package com.example.weatherstate.ui.base

import androidx.lifecycle.ViewModel
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.internal.UnitSystem
import com.example.weatherstate.internal.lazyDeferred

abstract class WeatherViewModel(
        private val  weatherStateRepository: WeatherStateRepository,
        unitProvider: UnitProvider
): ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    // hata : Suspend function 'getCurrentWeather' should be called only from a coroutine or another suspend function
    // Yukarıdaki hatayı düzeltmek için internal dosyası içerisindeki  Delegates.kt de oluşturduğumuz lazyDeferreed fonksiyonu ile getWeatherLocation da oluşan suspend hatasını yok etmek için lazyDeferred funksiyonunu atamış olduk.
    val weatherLocation by lazyDeferred {
        weatherStateRepository.getWeatherLocation()
    }

}