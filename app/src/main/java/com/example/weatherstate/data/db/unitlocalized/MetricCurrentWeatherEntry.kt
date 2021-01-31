package com.example.weatherstate.data.db.unitlocalized

import androidx.room.ColumnInfo

class MetricCurrentWeatherEntry(
        @ColumnInfo(name ="temperature")
        override val temperature: Double,
//        @ColumnInfo(name ="condition_text")
//        override val conditionText: String,
//        @ColumnInfo(name ="condition_icon")
//        override val conditionIcon: String,
        @ColumnInfo(name ="windSpeed")
        override val windSpeed: Double,
        @ColumnInfo(name ="windDir")
        override val windDirection: String,
        @ColumnInfo(name ="precip")
        override val precipitationVolume: Double,
        @ColumnInfo(name ="feelslike")
        override val feelsLikeTemperature: Double,
        @ColumnInfo(name ="visibility")
        override val visibilityDistance: Double

) : UnitSpecificCurrentWeatherEntry