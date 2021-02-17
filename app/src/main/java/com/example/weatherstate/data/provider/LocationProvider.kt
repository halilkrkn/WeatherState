package com.example.weatherstate.data.provider

import com.example.weatherstate.data.db.entity.WeatherLocation

// Location Provider = Lokasyok Sağlayıcı
interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString():String


}