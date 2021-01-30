package com.example.weatherstate.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {

    val temperature: Double
//    val conditionText: String
//    val conditionIcon: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double


}