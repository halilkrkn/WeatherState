package com.example.weatherstate.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.CurrentWeatherDao
import com.example.weatherstate.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class WeatherStateRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherStateRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)

        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
      initialWeatherData()
       return withContext(Dispatchers.IO){
           return@withContext currentWeatherDao.getWeatherMetric()

       }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO){

            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)

        }
    }

    private suspend fun initialWeatherData(){
            if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
                fetchCurrentWeather()

    }

    //Güncel hava durumu bilgilerini getirmek için location ve language tanımladık.
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("Osmaniye",
                Locale.getDefault().language
        )
    }

    //Gerekli hava durumu güncellemeleri getirmek için bir zaman aralığına koyduk.
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}