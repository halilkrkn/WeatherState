package com.example.weatherstate.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.weatherstate.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"


class UnitProviderImpl(context: Context): PreferenceProvider(context),UnitProvider {

    // Burdaki key res içerisindeki xml dosyasındaki preferences xml i içerisinde ListPreference da tanımladığımız key
    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }


}