package com.example.weatherstate.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherstate.data.db.converter.CurrentWeatherEntryConverters
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
//@TypeConverters(CurrentWeatherEntryConverters::class)
data class CurrentWeatherEntry(
        val feelslike: Double,
        @SerializedName("is_day")
        val isDay: String,
        val precip: Double,
        val temperature: Double,
        //Embedded = Field ı açıklama yapmak için kullanılır.
        val visibility: Double,
        @SerializedName("weather_code")
        val weatherCode: Double,
        @SerializedName("weather_descriptions")
        val weatherDescriptions: List<String>,
        @SerializedName("weather_icons")
        val weatherIcons: List<String>,
        @SerializedName("wind_dir")
        val windDir: String,
        @SerializedName("wind_speed")
        val windSpeed: Double
){
        @PrimaryKey(autoGenerate = false)
         var dbId: Int = CURRENT_WEATHER_ID
}
//{
//    //Primary key sırasıyla artan bir değer olmasını istemediğimiz için autoGenerateyi false yaptık.
//    @PrimaryKey(autoGenerate = false)
//    var id: Int = CURRENT_WEATHER_ID

//}

// Entity = Modelimizi temsil eder. Model sınıfının adı tablo adını alır.
// Bknz: https://medium.com/@cagataygull/room-k%C3%BCt%C3%BCphanesi-nedir-nas%C4%B1l-kullan%C4%B1l%C4%B1r-android-kotlin-2bc378107c05
// Bknz: https://speednet.pl/blog/room-persistence-library-introduction-part-1/
// Bknz: https://speednet.pl/blog/room-persistence-library-introduction-part-2/
// Bknz: https://developer.android.com/training/data-storage/room
// Bknz: https://developer.android.com/jetpack/androidx/releases/room
// Bknz: https://mertkesgin.com/room-database/