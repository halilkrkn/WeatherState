package com.example.weatherstate.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Room database indices (endeskler) i kullanarak Index içerinde sütunları isimlerini tanımladık ve eşsiz olduğunu belirttik.

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val date: String,
        @Embedded
        val day: Day
)