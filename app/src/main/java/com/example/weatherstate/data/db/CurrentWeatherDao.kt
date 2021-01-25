package com.example.weatherstate.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherstate.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherstate.data.db.entity.CurrentWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.MetricCurrentWeatherEntry


@Dao
interface CurrentWeatherDao {
    //upsert = hemde update hemde insert işlemleri için oluşturulmuş bir fonksiyondur.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)


    //Sqlite kodlarını yazarak oluşturduğumuz current_weather tablosunu id üzerinden bir  sorgu işlemi yaptık.
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>
}

// TODO: 25.01.2021
//Dao = (Data Access Object — Veri Erişim Nesnesi), veri tabanına erişim yöntemidir. Kullanıcı ile veritabanı arasında aracılık yapan bir ara birimdir.Bir tabloda gerçekleştirilecek tüm işlem burada tanımlanmalıdır.
//Oluşturduğumuz Interface’lerinin başına @Dao yazdığımızda Room Kütüphanesi database için gerekli fonksiyonları oluşturacaktır. Bunlar Insert, Delete, Update ve Query’dir.
//Bknz:https://medium.com/@cagataygull/room-k%C3%BCt%C3%BCphanesi-nedir-nas%C4%B1l-kullan%C4%B1l%C4%B1r-android-kotlin-2bc378107c05
//Bknz: https://medium.com/@s.sunayyildiz/room-nedir-android-studio-34ce4cd43b03
//Bknz: https://developer.android.com/training/data-storage/room
