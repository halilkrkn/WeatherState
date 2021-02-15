package com.example.weatherstate.data.network.response


import com.example.weatherstate.data.db.entity.CurrentWeatherEntry
import com.example.weatherstate.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
        @SerializedName("current")
        val currentWeatherEntry: CurrentWeatherEntry,
        val location: WeatherLocation
)