package com.example.weatherstate.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.CurrentWeatherDao
import com.example.weatherstate.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

//Bu kısımda oluşturuduğumuz WeaatherStateRepository interface indeki tanımlaıdğımız özzellikleri implemente ettim ve dışarıdan almak istediğim veri tanımladık.
class WeatherStateRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherStateRepository {

    // Burada ise persistFetchedCurrentWeather fonk. na yeni hava durumu verilerini atadık.
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)

        }
    }

    // Burada ise kalıcı bir şekilde mevcut hava durumunu için currentWeatherDao da oluşturduğumuz upsert fonksiyonunu tanımladık ki yeni güncel hava durumunu init {} işleminden alıp bu oluşturduğumuz fonksiyona atadık. ve bu hava durumunu ise upsert fonk ile database e ekledik veya güncelledik.
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO){

            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)

        }
    }


    // implemente ettiğimiz özellik olan getCurrentWeather ı uı da gösterebilmek için currentWeatherDao da oluşturduğumuz getWeatherMetric() fonksiyonunu  tanımladık ki oradaki istenilen verileri uı ui da gösteerelim
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
      initialWeatherData()
       return withContext(Dispatchers.IO){
           return@withContext if (metric) currentWeatherDao.getWeatherMetric()
           else currentWeatherDao.getWeatherImperial()

       }
    }


    // İlk HavaDurumu Verilerini  zamana göre  her saatte bir güncelledik..
    private suspend fun initialWeatherData(){
            if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
                fetchCurrentWeather()

    }

    //Gerekli hava durumu güncellemeleri getirmek için bir zaman aralığına koyduk.
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    //Güncel hava durumu bilgilerini getirmek için location ve language tanımladık.
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("Osmaniye",
                Locale.getDefault().language
        )
    }

}