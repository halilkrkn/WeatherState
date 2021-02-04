package com.example.weatherstate.data.db.unitlocalized

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.example.weatherstate.data.db.converter.CurrentWeatherEntryConverters

//@TypeConverters(CurrentWeatherEntryConverters::class)
class MetricCurrentWeatherEntry(
        @ColumnInfo(name ="temperature")
        override val temperature: Double,
        @ColumnInfo(name ="windSpeed")
        override val windSpeed: Double,
        @ColumnInfo(name ="windDir")
        override val windDirection: String,
        @ColumnInfo(name ="precip")
        override val precipitationVolume: Double,
        @ColumnInfo(name ="feelslike")
        override val feelsLikeTemperature: Double,
        @ColumnInfo(name ="visibility")
        override val visibilityDistance: Double,
        @ColumnInfo(name ="weatherDescriptions")
        override val weatherDescription: List<String>,
        @ColumnInfo(name ="weatherIcons")
        override val weatherIcon: List<String>

) : UnitSpecificCurrentWeatherEntry