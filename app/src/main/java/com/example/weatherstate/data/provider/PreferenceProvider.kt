package com.example.weatherstate.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

//PreferenceProvider = Öncelikli(Tercih Edilen) sağlayıcı
abstract class PreferenceProvider(context: Context) {

   private val appContext = context.applicationContext

//    shared preferences ile küçük boyutta verileri hafıza tutabiliyoruz.
    protected val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


}