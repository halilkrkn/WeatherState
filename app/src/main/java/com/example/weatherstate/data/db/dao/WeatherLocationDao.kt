package com.example.weatherstate.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherstate.data.db.entity.CURRENT_LOCATION_ID
import com.example.weatherstate.data.db.entity.WeatherLocation

//Dao = Data Access Object = Veri Erişim Nesnesidir. Yani Veri tabanına erişim için bu kısımdn işlemler yürütülür.
@Dao
interface WeatherLocationDao {

    // Veri tabanına lokasyon ve zaman bilgilerini kayıt edip varsa yeni zamanı üzerine yazıp kayıt etme işlemi yaptık.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert (weatherLocation: WeatherLocation)


    // Veri tabanından sorgu yapılarak weather_location ın id si çekiktik ve getLocation fonksiyonu ile bu verileri canlı bir şekilde WeatherLocation sınıfından çekip Uı da göstermek için kullanılack.
    @Query("select * from weather_location where locationId = $CURRENT_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("select * from weather_location where locationId = $CURRENT_LOCATION_ID")
    fun getLocationNonLive(): WeatherLocation

}