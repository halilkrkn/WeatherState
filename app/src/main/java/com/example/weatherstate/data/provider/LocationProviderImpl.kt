package com.example.weatherstate.data.provider

import com.example.weatherstate.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPrefferedLoacationString(): String {
        return "Osmaniye"
    }
}