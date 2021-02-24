package com.example.weatherstate.data.network.response


import com.example.weatherstate.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(

        @SerializedName("forecastday")
        val entries: List<FutureWeatherEntry>
)