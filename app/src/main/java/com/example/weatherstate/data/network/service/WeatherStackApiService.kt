package com.example.weatherstate.data.network.service

import com.example.weatherstate.data.network.ConnectivityInterceptor
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import com.example.weatherstate.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



const val API_KEY = "cc776121ebf647d98e3132735210702"
const val BASE_URL ="https://api.weatherapi.com/v1/"


// Bu oluşturduğumuz interface ile bir sevis olluşturduk ve api üzerindeki verileri sağlıklı ve düzenli bir şekilde çekmemize yarıyacak kodları yazdık.
interface WeatherStackApiService {

    // CurrentWeather
    //https://api.weatherapi.com/v1/current.json?key=cc776121ebf647d98e3132735210702&q=Osmaniye&lang=tr
    @GET("current.json")
    fun getCurrentWeather (
        @Query("q") location: String,
        @Query("lang")  languageCode: String ="tr"
    ): Deferred<CurrentWeatherResponse>


    // FutureWeather
    //https://api.weatherapi.com/v1/forecast.json?key=cc776121ebf647d98e3132735210702&q=osmaniye&days=1
    @GET("forecast.json")
    fun getFutureWeather (
            @Query("q") location: String,
            @Query("days") days: Int,
            @Query("lang")  languageCode: String ="tr"
    ): Deferred<FutureWeatherResponse>

    companion object{

        operator fun invoke(

                connectivityInterceptor: ConnectivityInterceptor

        ): WeatherStackApiService {

            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }
            
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherStackApiService::class.java)
        }
    }
}



// TODO: 25.01.2021
//  Yani Deferred(ertelenmiş değer) aslında Job(iş)tir.
//  Ertelenmiş değer, engellemeyen, iptal edilebilir bir gelecektir - sonucu olan bir İştir.
//  Deferred: bir durumu başlattıktan sonra bir noktada bitecek olan bir işlemi kapsüller.


// TODO: 25.01.2021 Interceptor = Önleyiciler, aramaları izleyebilen, yeniden yazabilen ve yeniden deneyebilen güçlü bir mekanizmadır.