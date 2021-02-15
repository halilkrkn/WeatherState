package com.example.weatherstate.data.provider

import com.example.weatherstate.data.db.entity.WeatherLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPrefferedLoacationString():String


}