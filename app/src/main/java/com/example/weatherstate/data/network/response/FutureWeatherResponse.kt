package com.example.weatherstate.data.network.response


import com.example.weatherstate.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
        @SerializedName("forecast")
        val futureWeatherEntries: ForecastDaysContainer,
        val location: WeatherLocation

)