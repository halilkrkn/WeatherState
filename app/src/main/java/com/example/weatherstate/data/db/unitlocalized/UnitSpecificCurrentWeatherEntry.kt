package com.example.weatherstate.data.db.unitlocalized

import androidx.room.TypeConverters
import com.example.weatherstate.data.db.converter.CurrentWeatherEntryConverters

//@TypeConverters(CurrentWeatherEntryConverters::class)
interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
    val windSpeed: Double
    val weatherDescription: List<String>
    val weatherIcon: List<String>
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double


}