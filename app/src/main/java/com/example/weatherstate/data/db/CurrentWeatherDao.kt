package com.example.weatherstate.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherstate.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherstate.data.db.entity.CurrentWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.MetricCurrentWeatherEntry

//DAO, veritabanına erişen yöntemlerin tanımlanmasından sorumludur.
//SQL sorgularımızı koyduğumuz bir Interface‘dir.
//Dao Class tanımlarken @Dao, SQL sorgusu yazarken @Insert, @Delete, @Update, @Query annotationlarını kullanarak veritabanı işlemlerini yapıyoruz.
@Dao
interface CurrentWeatherDao {

    // Hem update hemde insert işlemlerinin yapıldığı bir fonksiyondur.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)


    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek MetricCurrentWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from current_weather where dbId = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek ImperialCurrentWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from current_weather where dbId = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}

// Bknz: https://medium.com/@cagataygull/room-k%C3%BCt%C3%BCphanesi-nedir-nas%C4%B1l-kullan%C4%B1l%C4%B1r-android-kotlin-2bc378107c05
// Bknz: https://mertkesgin.com/room-database/