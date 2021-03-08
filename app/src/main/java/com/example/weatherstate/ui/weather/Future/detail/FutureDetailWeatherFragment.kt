package com.example.weatherstate.ui.weather.Future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherstate.data.db.converters.LocalDateConverter
import com.example.weatherstate.internal.DateNotFoundException
import com.example.weatherstate.internal.glide.GlideApp
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.android.synthetic.main.future_detail_weather_fragment.imageView_conditionIcon
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_precipitation
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_temperature
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_visibility
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_weather_conditionText
import kotlinx.android.synthetic.main.future_detail_weather_fragment.textView_wind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherstate.R
import com.example.weatherstate.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class FutureDetailWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory:((LocalDate) -> FutureDetailWeatherViewModelFactory ) by factory()
    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it)}
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProviders.of(this,viewModelFactoryInstanceFactory(date)).get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureweather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer{ location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureweather.observe(viewLifecycleOwner, Observer{ weatherEntry ->
            if (weatherEntry == null) return@Observer

            group_loading_detail.visibility = View.GONE
            updateDate(weatherEntry.date)
            updateTemperatures(weatherEntry.avgTemperature,weatherEntry.maxTemperature,weatherEntry.minTemperature)
            updateConditionText(weatherEntry.conditionText)
            updatePrecipitation(weatherEntry.totalPrecipitation)
            updateWindSpeed(weatherEntry.maxWindSpeed)
            updateVisibility(weatherEntry.avgVisibilityDistance)
            updateUv(weatherEntry.uv)

            GlideApp.with(this@FutureDetailWeatherFragment)
                    .load("http:" + weatherEntry.conditionIconUrl)
                    .into(imageView_conditionIcon)


        })

    }

    private fun updateUv(uv: Double) {
        textView_feels_uv.text = "UV: $uv"
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String{
        return if(viewModel.isMetricUnit) metric else imperial
    }

    //ActionBarda  tanımladığımız lokasyonu gösterdik.
    private fun updateLocation(location: String){

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    //ActionBarda  günü  gösterdik.
    private fun updateDate(date: LocalDate){

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C","°F")
        textView_temperature.text ="$temperature$unitAbbreviation"
        textView_feels_minTemp.text ="Min: $min$unitAbbreviation"
        textView_maxTemp.text ="Max $max$unitAbbreviation"
    }

    private fun updateConditionText(conditionText: String){
        textView_weather_conditionText.text = conditionText
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm","in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"

    }

    private fun updateWindSpeed(windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph","mph")
        textView_wind.text = "Wind Speed: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km","miles")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}