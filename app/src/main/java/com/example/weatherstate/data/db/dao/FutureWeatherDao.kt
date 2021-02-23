package com.example.weatherstate.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherstate.data.db.entity.FutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.ImperialSimpleFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface FutureWeatherDao {

    // insert işlemlerinin yapıldığı bir fonksiyondur.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: FutureWeatherEntry)

    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek MetricSimpleFutureWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from future_weather where date(date) >= date(:startDate)" )
    fun getSimpleFFutureWeatherMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek ImperialSimpleFutureWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from future_weather where date(date) >= date(:startDate)" )
    fun getSimpleFFutureWeatherImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    // Buradaki sorgu günlere göre gelecek hava durumu bilgilerinin günlerini veri tabanından çekiyoruz. Bu şekilde gün gün gelecek hava durumu biliglerine erişimi sağlatmış oluyoruz.
    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate : LocalDate) : Int

    // Buradaki delete sorgu işlemi önceki güncelerden kala verileri silip güncel gelecek verileri eklemek için bu sorguyu kullanıyoruz.
    @Query("delete from future_weather where daate(date) < date(:fistDateToKeep)")
    fun deleteOldEntries(fistDateToKeep: LocalDate)

}