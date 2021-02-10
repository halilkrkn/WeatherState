package com.example.weatherstate.data.network.service

import com.example.weatherstate.data.network.ConnectivityInterceptor
import com.example.weatherstate.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.weatherapi.com/v1/current.json?key=0a8c767d8a91484290d132732210702&q=Osmaniye&Lang=tr
//https://api.weatherapi.com/v1/current.json?key=cc776121ebf647d98e3132735210702&q=Osmaniye
const val API_KEY = "cc776121ebf647d98e3132735210702"
//const val BASE_URL ="https://api.weatherapi.com/v1/"


// Bu oluşturduğumuz interface ile bir sevis olluşturduk ve api üzerindeki verileri sağlıklı ve düzenli bir şekilde çekmemize yarıyacak kodları yazdık.

interface WeatherStackApiService {

    @GET("current.json")
    fun getCurrentWeather (
        @Query("query") location: String,
        @Query("lang")  languageCode: String ="tr"
    ): Deferred<CurrentWeatherResponse>


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
                    .baseUrl("https://api.weatherapi.com/v1/")
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