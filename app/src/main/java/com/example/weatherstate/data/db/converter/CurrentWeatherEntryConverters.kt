package com.example.weatherstate.data.db.converter

import androidx.room.TypeConverter
import com.example.weatherstate.data.db.entity.CurrentWeatherEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class CurrentWeatherEntryConverters {

    private val gson = Gson()

    @TypeConverter
    fun listToJson(value: List<String>?) = gson.toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = gson.fromJson(value,Array<String>::class.java).toList()

/*
// TODO: 7.02.2021 Yukarıdaki method ile aynı dönüştürme(converter) işlemidir
//    @TypeConverter
//    fun fromString(value: String?): List<String>{
//        if (value == null){
//            return Collections.emptyList()
//        }
//        val listType = object: TypeToken<List<String>>(){}.type
//        return gson.fromJson(value,listType)
//    }
//    @TypeConverter
//    fun toStringList(list: List<String?>): String{
//        return gson.toJson(list)
//    }
*/
}
