package com.example.weatherstate.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherstate.R
import com.example.weatherstate.internal.glide.GlideApp
import com.example.weatherstate.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    // Kodein ile oluşturduğumuz bağımlılıkları çağırmak için KodeinAware interface ini çağrıp closestKodein i override edip bağımlılıkları çalıştırdık.
    override val kodein by closestKodein()
    // Burada da WeatherStateApplication da bind(bağladığımız) CurrentWeatherViewModelFactory i tanımladık.
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    // bu kısımda CurrentWeatherViewModel inin çağırdık ve bu sayede Api ve Database den çekilen verileri UI da gösterileceğiz.
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentWeatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    // Bu fonksiyonda api üzerindeki verileri database e ekleyip sonrada repository içerisinden istenilen verileri çektik ve UI da gösterdik.
    private fun bindUI() = launch {

        val currentWeather = currentWeatherViewModel.weather.await()
        val weatherLocation = currentWeatherViewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null ) return@Observer
            updateLocation(location.name)
        })


        currentWeather.observe(viewLifecycleOwner, Observer { currentWeatherEntry ->
            if (currentWeatherEntry == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperatures(currentWeatherEntry.temperature, currentWeatherEntry.feelsLikeTemperature)
            updateConditionText(currentWeatherEntry.conditionText)
            updatePrecipitation(currentWeatherEntry.precipitationVolume)
            updateWind(currentWeatherEntry.windDirection, currentWeatherEntry.windSpeed)
            updateVisibility(currentWeatherEntry.visibilityDistance)


            GlideApp.with(this@CurrentWeatherFragment)
                    .load("http:${currentWeatherEntry.conditionIconUrl}")
                    .into(imageView_conditionIcon)


        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        return if(currentWeatherViewModel.isMetricUnit) metric else imperial
    }

    //ActionBarda  tanımladığımız lokasyonu gösterdik.
    private fun updateLocation(location: String){

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }


    //ActionBarda  günü  gösterdik.
    private fun updateDateToToday(){

            (activity as? AppCompatActivity)?.supportActionBar?.setSubtitle(R.string.today)

    }

//    Feels Like
    private fun updateTemperatures(temperature: Double, feelsLike: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C","°F")
        textView_temperature.text ="$temperature$unitAbbreviation"
        textView_feels_like_temperature.text ="$feelsLike$unitAbbreviation"
    }

    private fun updateConditionText(conditionText: String){
        textView_weather_conditionText.text = conditionText
    }

//    Precipitation
    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm","in")
        textView_precipitation.text = "$precipitationVolume $unitAbbreviation"

    }
//    Wind:
    private fun updateWind(windDirection:String, windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph","mph")
        textView_wind.text = "$windDirection $windSpeed $unitAbbreviation"
    }

//    Visibility
    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km","miles")
        textView_visibility.text = " $visibilityDistance $unitAbbreviation"

    }


}