package com.example.weatherstate.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherstate.R
import com.example.weatherstate.data.network.ConnectivityInterceptorImpl
import com.example.weatherstate.data.network.WeatherNetworkDataSource
import com.example.weatherstate.data.network.WeatherNetworkDataSourceImpl
import com.example.weatherstate.data.network.service.WeatherStackApiService
import com.example.weatherstate.internal.glide.GlideApp
import com.example.weatherstate.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    // Kodein ile oluşturduğumuz bağımlılıkları çağırmak için KodeinAware interface ini çağrıp closestKodeini override edip bağımlılıkları çalıştırdık.
    override val kodein by closestKodein()
    // Burada da WeatherStateApplication da bind(bağladığımız) CurrentWeatherViewModelFactory i tanımladık.
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }
/* TODO: Önceki kullanım şekli 1

    companion object {
//        fun newInstance() = CurrentWeatherFragment()
    }

 */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentWeatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()


/*
        // TODO: Önceki kullanım Şekli 2


//        val apiService = WeatherStackApiService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
//            textCurrent.text = it.toString()
//
//        })
//
//        GlobalScope.launch(Dispatchers.Main){
//            weatherNetworkDataSource.fetchCurrentWeather("Osmaniye","tr")
//        }
        */
    }

    private fun bindUI() = launch {

        val currentWeather = currentWeatherViewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Osmaniye")
            updateDatetoToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.weatherDescription)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                    .load("http:${it.weatherIcon}")
                    .into(imageView_weather_icon)

        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String): String{
        return if(currentWeatherViewModel.isMetric) metric else metric
    }

    private fun updateLocation(location: String){

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDatetoToday(){

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C")
        textView_temperature.text ="$temperature $unitAbbreviation"
        textView_feels_like_temperature.text ="$feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: List<String>){
        textView_weather.text = condition.toString()
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"

    }
    private fun updateWind(windDirection:String, windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph")
        textView_precipitation.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }
    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°km")
        textView_precipitation.text = "Visibility: $visibilityDistance $unitAbbreviation"

    }


}