package com.example.weatherstate.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import com.example.weatherstate.data.network.service.WeatherStackApiService
import com.example.weatherstate.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
        private val weatherStackApiService: WeatherStackApiService
) : WeatherNetworkDataSource {

      private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = weatherStackApiService
                    .getCurrentWeather(location, languageCode)
                    .await()
            
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e : NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }
}