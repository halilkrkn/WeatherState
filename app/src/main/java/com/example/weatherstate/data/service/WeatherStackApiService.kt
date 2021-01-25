package com.example.weatherstate.data.service

import android.telephony.cdma.CdmaCellLocation
import com.example.weatherstate.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "651ccaea42ebedb6d9caa37582355229"

// http://api.weatherstack.com/current?access_key=651ccaea42ebedb6d9caa37582355229&query=Ankara&Lang=tr
interface WeatherStackApiService {

    @GET("current")
    fun getCurrentWeather (
        @Query("query") location: String,
        @Query("lang")  languageCode: String ="tr"
    ): Deferred<CurrentWeatherResponse>
    // TODO: 25.01.2021
    //  Yani Deferred(ertelenmiş değer) aslında Job(iş)tir.
    //  Ertelenmiş değer, engellemeyen, iptal edilebilir bir gelecektir - sonucu olan bir İştir.
    //  Deferred: bir durumu başlattıktan sonra bir noktada bitecek olan bir işlemi kapsüller.

    companion object{

        operator fun invoke(): WeatherStackApiService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("access_key", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }
            
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api.weatherstack.com/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherStackApiService::class.java)
        }
    }
}