package com.example.weatherstate.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherstate.data.db.entity.FutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.detail.ImperialDetailFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.detail.MetricDetailFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {

    // insert işlemlerinin yapıldığı bir fonksiyondur.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek MetricSimpleFutureWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from future_weather where date(date) >= date(:startDate)" )
    fun getSimpleFutureWeatherMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    //Burada sql sorgusu yaparak oluşturuduğumuz Entity içerisindeki verilere erişerek ImperialSimpleFutureWeatherEntry deki verilere uygun olanları alıp LiveData içerisine koyup sonrada ui da canlı bir şekilde gözükmesi için yapıyoruz.
    @Query("select * from future_weather where date(date) >= date(:startDate)" )
    fun getSimpleFutureWeatherImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailedWeatherByDateMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailedWeatherByDateImperial(date: LocalDate): LiveData<ImperialDetailFutureWeatherEntry>

    // Buradaki sorgu günlere göre gelecek hava durumu bilgilerinin günlerini veri tabanından çekiyoruz. Bu şekilde gün gün gelecek hava durumu biliglerine erişimi sağlatmış oluyoruz.
    @Query("select count(futureId) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate : LocalDate) : Int

    // Buradaki delete sorgu işlemi önceki güncelerden kala verileri silip güncel gelecek verileri eklemek için bu sorguyu kullanıyoruz.
    @Query("delete from future_weather where date(date) < date(:fistDateToKeep)")
    fun deleteOldEntries(fistDateToKeep: LocalDate)

}