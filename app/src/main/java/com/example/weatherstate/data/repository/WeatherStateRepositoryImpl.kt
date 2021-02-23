package com.example.weatherstate.data.repository


import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.dao.CurrentWeatherDao
import com.example.weatherstate.data.db.dao.WeatherLocationDao
import com.example.weatherstate.data.db.entity.WeatherLocation
import com.example.weatherstate.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import com.example.weatherstate.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

//Bu kısımda oluşturuduğumuz WeaatherStateRepository interface indeki tanımlaıdğımız özzellikleri implemente ettim ve dışarıdan almak istediğim veri tanımladık.
// Aslında Bütün verileri bu depodan yönetiyoruz.
class WeatherStateRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherLocationDao: WeatherLocationDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val locationProvider: LocationProvider
) : WeatherStateRepository {

    // TODO: ******** getCurrentWeather fonksiyonu implementi*********
    // implemente ettiğimiz özellik olan getCurrentWeather ı uı da gösterebilmek için currentWeatherDao da oluşturduğumuz getWeatherMetric() fonksiyonunu  tanımladık ki oradaki istenilen verileri uı ui da gösteerelim
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        initialWeatherData()
        return withContext(Dispatchers.IO){
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()

        }
    }


    // TODO: ******** getWeatherLocation fonksiyonu implementi*********
    // Bu fonksiyonu WeatherStateRepository interface inden implmenete ederek getWeatherLocation fonksiyonunu yönetebileceğiz ve oradaki bilgileri Uı da kullanabileceğiz.
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }


    // Burada ise persistFetchedCurrentWeather fonk. na yeni hava durumu verilerini atadık.
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)

        }
    }

    // Burada ise kalıcı bir şekilde mevcut hava durumunu için currentWeatherDao da oluşturduğumuz upsert fonksiyonunu tanımladık ki yeni güncel hava durumunu init {} işleminden alıp bu oluşturduğumuz fonksiyona atadık. ve bu hava durumunu ise upsert fonk ile database e ekledik veya güncelledik.
    // Burada aynı işlemleri yaparak weatherLocationDao dan upsert fonksiyonunu çekip lokasyon verilerini database ekledik veya güncelledik.
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }


    // İlk HavaDurumu Verilerini  zamana göre  her saatte bir güncelledik..
    private suspend fun initialWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        //burada WeatherLocation classında yapmış olduğumuz zonedDateTime ı çektik.
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }


    //Güncel hava durumu bilgilerini getirmek için location ve language tanımladık.
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(),
                Locale.getDefault().language
        )
    }

    //Gerekli hava durumu güncellemeleri getirmek için bir zaman aralığına koyduk.
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }



}