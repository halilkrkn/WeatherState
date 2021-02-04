package com.example.weatherstate.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherEntryConverters {

    private val gson = Gson()

    @TypeConverter
    fun listToJson(value: List<String>?) = gson.toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = gson.fromJson(value,Array<String>::class.java).toList()



//    @TypeConverter
//    fun fromString(value: String?): List<String>{
//
//        val listType = object: TypeToken<List<String>>(){}.type
//        return gson.fromJson(value,listType)
//    }
//
//    fun toStringList(list:List<String?>): String{
//
//        return gson.toJson(list)
//    }
}