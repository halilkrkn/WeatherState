package com.example.weatherstate

import android.app.Application
import com.example.weatherstate.data.db.WeatherStateDatabase
import com.example.weatherstate.data.network.ConnectivityInterceptor
import com.example.weatherstate.data.network.ConnectivityInterceptorImpl
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherstate.data.network.service.WeatherStackApiService
import com.example.weatherstate.data.repository.WeatherStateRepository
import com.example.weatherstate.data.repository.WeatherStateRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


//Kotlin Dependency Injection (Kodein) işlemleri
class WeatherStateApplication : Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherStateApplication))

        //Bağımlılıkları bağlama işlemleri yapılıyor
        bind() from singleton { WeatherStateDatabase(instance()) }
        bind() from singleton { instance<WeatherStateDatabase>().getCurrentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton {ConnectivityInterceptorImpl(instance())}
        bind() from singleton {WeatherStackApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton {WeatherNetworkDataSourceImpl(instance())}
        bind<WeatherStateRepository>() with singleton { WeatherStateRepositoryImpl(instance(),instance()) }


    }
}