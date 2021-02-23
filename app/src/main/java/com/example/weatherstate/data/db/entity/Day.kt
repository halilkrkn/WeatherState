package com.example.weatherstate.data.db.entity


import androidx.room.Embedded
import com.example.weatherstate.data.db.entity.Condition
import com.google.gson.annotations.SerializedName

data class Day(

        @SerializedName("avgtemp_c")
        val avgtempC: Double,
            @SerializedName("avgtemp_f")
        val avgtempF: Double,
            @SerializedName("avgvis_km")
        val avgvisKm: Double,
            @SerializedName("avgvis_miles")
        val avgvisMiles: Double,
        @Embedded(prefix = "condition_",)
        val condition: Condition,
        @SerializedName("daily_chance_of_rain")
        val dailyChanceOfRain: String,
            @SerializedName("daily_chance_of_snow")
        val dailyChanceOfSnow: String,
            @SerializedName("daily_will_it_rain")
        val dailyWillİtRain: Int,
            @SerializedName("daily_will_it_snow")
        val dailyWillİtSnow: Int,
            @SerializedName("maxtemp_c")
        val maxtempC: Double,
            @SerializedName("maxtemp_f")
        val maxtempF: Double,
            @SerializedName("maxwind_kph")
        val maxwindKph: Double,
            @SerializedName("maxwind_mph")
        val maxwindMph: Double,
            @SerializedName("mintemp_c")
        val mintempC: Double,
            @SerializedName("mintemp_f")
        val mintempF: Double,
            @SerializedName("totalprecip_mm")
        val totalprecipMm: Double,
            @SerializedName("totalprecip_in")
        val totalprecipİn: Double,
        val uv: Double
)