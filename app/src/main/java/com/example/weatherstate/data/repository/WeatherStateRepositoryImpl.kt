package com.example.weatherstate.data.repository


import androidx.lifecycle.LiveData
import com.example.weatherstate.data.db.dao.CurrentWeatherDao
import com.example.weatherstate.data.db.dao.FutureWeatherDao
import com.example.weatherstate.data.db.dao.WeatherLocationDao
import com.example.weatherstate.data.db.entity.WeatherLocation
import com.example.weatherstate.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.example.weatherstate.data.network.FORECAST_DAYS_COUNT
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import com.example.weatherstate.data.network.response.FutureWeatherResponse
import com.example.weatherstate.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

//Bu kısımda oluşturuduğumuz WeaatherStateRepository interface indeki tanımlaıdğımız özzellikleri implemente ettim ve dışarıdan almak istediğim veri tanımladık.
// Aslında Bütün verileri bu depodan yönetiyoruz.
class WeatherStateRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherLocationDao: WeatherLocationDao,
        private val futureWeatherDao: FutureWeatherDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val locationProvider: LocationProvider
) : WeatherStateRepository {

    // TODO: ******** getCurrentWeather fonksiyonu implementi*********
    // implemente ettiğimiz özellik olan getCurrentWeather ı uı da gösterebilmek için currentWeatherDao da oluşturduğumuz getWeatherMetric() fonksiyonunu  tanımladık ki oradaki istenilen verileri uı ui da gösteerelim
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {

        return withContext(Dispatchers.IO){
            initialWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()

        }
    }

    // TODO: ******** getFutureWeatherList fonksiyonu implementi*********
    override suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initialWeatherData()
            return@withContext if(metric) futureWeatherDao.getSimpleFutureWeatherMetric(startDate)
            else futureWeatherDao.getSimpleFutureWeatherImperial(startDate)
        }
    }

    // TODO: ******** getDetailFutureWeatherByDate fonksiyonu implementi*********
    override suspend fun getDetailFutureWeatherByDate(
            startDate: LocalDate,
            metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO){
            initialWeatherData()
            return@withContext if(metric) futureWeatherDao.getDetailedWeatherByDateMetric(startDate)
            else futureWeatherDao.getDetailedWeatherByDateImperial(startDate)
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
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)

            }
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


    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse){
       fun deleteOldForecastData(){
           val today = LocalDate.now()
           futureWeatherDao.deleteOldEntries(today)
       }

        GlobalScope.launch(Dispatchers.IO){
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }



    // İlk HavaDurumu Verilerini  zamana göre  her saatte bir güncelledik..
    private suspend fun initialWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        //burada WeatherLocation classında yapmış olduğumuz zonedDateTime ı çektik.
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if(isFetchFutureNeeded())
            fetchFutureWeather()
    }


    //Güncel hava durumu bilgilerini getirmek için location ve language tanımladık.
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
                locationProvider.getPreferredLocationString(),
                Locale.getDefault().language
        )
    }

    //Gelecek hava durumu bilgilerini getirmek için location ve language tanımladık.
    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.fetchFutureWeather(
                locationProvider.getPreferredLocationString(),
                Locale.getDefault().language
        )
    }

    //Gerekli güncel hava durumu güncellemeleri getirmek için bir zaman aralığına koyduk.
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    //Gerekli gelecek hava durumu güncellemeleri getirmek için bir zaman aralığına koyduk.
    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }



}