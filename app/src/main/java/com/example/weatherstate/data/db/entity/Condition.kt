package com.example.weatherstate.data.db.entity


import com.google.gson.annotations.SerializedName

data class Condition(
        @SerializedName("code")
        val code: Int,
        @SerializedName("icon")
        val cIcon: String,
        @SerializedName("text")
        val text: String
)