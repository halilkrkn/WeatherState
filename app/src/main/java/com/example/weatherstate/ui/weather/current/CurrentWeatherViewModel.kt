package com.example.weatherstate.ui.weather.current


import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.internal.lazyDeferred
import com.example.weatherstate.ui.base.WeatherViewModel


class CurrentWeatherViewModel(
        private val weatherStateRepository: WeatherStateRepository,
        private val unitProvider: UnitProvider
) : WeatherViewModel(weatherStateRepository,unitProvider) {


    // hata : Suspend function 'getCurrentWeather' should be called only from a coroutine or another suspend function
    // Yukarıdaki hatayı düzeltmek için internal dosyası içerisindeki Delegates.kt de oluşturduğumuz lazyDeferreed fonksiyonu ile getCurrentWeather da oluşan suspend hatasını yok etmek için lazyDeferred funksiyonunu atamış olduk.
    val weather by lazyDeferred {
        weatherStateRepository.getCurrentWeather(super.isMetricUnit)
    }
}
