package com.example.weatherstate.ui.weather.Future.list

import com.example.weatherstate.R
import com.example.weatherstate.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import com.example.weatherstate.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.example.weatherstate.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureWeatherItem(
        val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
): Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textView_futureWeather_conditionText.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }

    }

    // Bu kısımda layout da oluşturdupumuz item_future_weather xml dosyasını tanımladık.
    override fun getLayout(): Int {
        return R.layout.item_future_weather
    }

    private fun GroupieViewHolder.updateDate(){
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    private fun GroupieViewHolder.updateTemperature() {
        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C"
        else "°F"
        textView_temperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
    }
    private fun GroupieViewHolder.updateConditionImage(){
        GlideApp.with(this.containerView)
                .load("http:"+ weatherEntry.conditionIconUrl)
                .into(imageView_condition_icon)
    }


}