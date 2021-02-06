package com.example.weatherstate.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

//burda güncel haca durumu verileri için repository(depo) oluşturuyoruz.
// UI da gösterebilmek için böyle bir fonk. oluşturduk interfface in içerisinde.
interface WeatherStateRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData< out UnitSpecificCurrentWeatherEntry>
}