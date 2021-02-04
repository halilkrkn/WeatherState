package com.example.weatherstate.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

//burda güncel haca durumu verileri için repository(depo) oluşturuyoruz.
interface WeatherStateRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData< out UnitSpecificCurrentWeatherEntry>
}