package com.example.weatherstate.data.db.unitlocalized

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.example.weatherstate.data.db.converter.CurrentWeatherEntryConverters

//@TypeConverters(CurrentWeatherEntryConverters::class)
data class MetricCurrentWeatherEntry(
        @ColumnInfo(name ="tempC")
        override val temperature: Double,
        @ColumnInfo(name ="windKph")
        override val windSpeed: Double,
        @ColumnInfo(name ="windDir")
        override val windDirection: String,
        @ColumnInfo(name ="precipMm")
        override val precipitationVolume: Double,
        @ColumnInfo(name ="feelslikeC")
        override val feelsLikeTemperature: Double,
        @ColumnInfo(name ="visKm")
        override val visibilityDistance: Double,
        @ColumnInfo(name ="condition_text")
        override val conditionText: String,
        @ColumnInfo(name ="condition_cIcon")
        override val conditionIconUrl: String

        ) : UnitSpecificCurrentWeatherEntry