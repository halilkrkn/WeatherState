package com.example.weatherstate.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.entity.WeatherLocation
import com.example.weatherstate.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

//burda güncel haca durumu verileri için repository(depo) oluşturuyoruz.
// UI da gösterebilmek için böyle bir fonk. oluşturduk interfface in içerisinde.
interface WeatherStateRepository {

//    güncel hva durumu verileri
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    // güncel hava durumu lokasyonları
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

    suspend fun getFutureWeatherList(startDate: LocalDate,metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
}