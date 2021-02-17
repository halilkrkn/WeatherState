package com.example.weatherstate

import android.app.Application
import android.content.Context
import com.example.weatherstate.data.db.WeatherStateDatabase
import com.example.weatherstate.data.network.ConnectivityInterceptor
import com.example.weatherstate.data.network.ConnectivityInterceptorImpl
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherstate.data.network.service.WeatherStackApiService
import com.example.weatherstate.data.provider.LocationProvider
import com.example.weatherstate.data.provider.LocationProviderImpl
import com.example.weatherstate.data.provider.UnitProvider
import com.example.weatherstate.data.provider.UnitProviderImpl
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.data.repository.WeatherStateRepositoryImpl
import com.example.weatherstate.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


//Kotlin Dependency Injection (Kodein) işlemleri ve bağımlılıkları bağlama
class WeatherStateApplication : Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherStateApplication))

        //Bağımlılıkları bağlama işlemleri yapılıyor
        bind() from singleton { WeatherStateDatabase(instance()) }
        bind() from singleton { instance<WeatherStateDatabase>().getCurrentWeatherDao() }
        bind() from singleton { instance<WeatherStateDatabase>().getWeatherLocationDao()}
        bind<ConnectivityInterceptor>() with singleton {ConnectivityInterceptorImpl(instance())}
        bind() from singleton {WeatherStackApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton {WeatherNetworkDataSourceImpl(instance())}
        bind<WeatherStateRepository>() with singleton { WeatherStateRepositoryImpl(instance(),instance(),instance(),instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton {LocationProviderImpl(instance(), instance())}
        bind<UnitProvider>() with singleton {UnitProviderImpl(instance())}
        bind() from provider {CurrentWeatherViewModelFactory(instance(),instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}