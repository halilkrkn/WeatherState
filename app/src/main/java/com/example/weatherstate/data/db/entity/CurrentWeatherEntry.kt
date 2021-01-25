package com.example.weatherstate.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

//Entity = Modelimizi temsil eder. Model sınıfının adı tablo adını alır.
//Bknz:https://medium.com/@cagataygull/room-k%C3%BCt%C3%BCphanesi-nedir-nas%C4%B1l-kullan%C4%B1l%C4%B1r-android-kotlin-2bc378107c05
//Bknz: https://medium.com/@s.sunayyildiz/room-nedir-android-studio-34ce4cd43b03
//Bknz: https://developer.android.com/training/data-storage/room
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val feelslike: Double,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,
    val temperature: Double,
    //Embedded = Field ı açıklama yapmak için kullanılır.
    @Embedded(prefix ="condition_")
    val condition: Condition,
    @SerializedName("uv_index")
    val uvIndex: Double,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
){
    //Primary key sırasıyla artan bir değer olmasını istemediğimiz için autoGenerateyi false yaptık.
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID

}