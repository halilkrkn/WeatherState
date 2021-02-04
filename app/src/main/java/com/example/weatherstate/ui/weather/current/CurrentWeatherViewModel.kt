package com.example.weatherstate.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.internal.UnitSystem
import com.example.weatherstate.internal.lazyDeferred

class CurrentWeatherViewModel(
        private val weatherStateRepository: WeatherStateRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC //get from settings later - ayarlardan daha sonra alıcak
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    // hata : Suspend function 'getCurrentWeather' should be called only from a coroutine or another suspend function
    // Yukarıdaki hatayı düzeltmek için Delegates.kt de oluşturduğumuz lazyDeferreed fonksiyonu ile getCurrentWeather da oluşan suspend hatasını yok etmek için lazyDeferred funksiyonunu atamış olduk.

    val weather by lazyDeferred {
        weatherStateRepository.getCurrentWeather(isMetric)
    }
}