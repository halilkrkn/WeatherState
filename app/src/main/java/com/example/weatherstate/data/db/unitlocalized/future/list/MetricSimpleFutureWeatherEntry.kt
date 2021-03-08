package com.example.weatherstate.data.db.unitlocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class MetricSimpleFutureWeatherEntry(
        @ColumnInfo(name = "date")
        override val date: LocalDate,
        @ColumnInfo(name ="avgtempC")
        override val avgTemperature: Double,
        @ColumnInfo(name ="condition_text")
        override val conditionText: String,
        @ColumnInfo(name ="condition_cIcon")
        override val conditionIconUrl: String
        ) : UnitSpecificSimpleFutureWeatherEntry
