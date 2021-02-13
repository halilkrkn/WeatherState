package com.example.weatherstate.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.weatherstate.R

class SettingsFragment: PreferenceFragmentCompat() {

    // res dosyası içerisinde oluşturduğuöuz xlm dosyası içerisindeki preferences xml dosyasını getirdik.
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }


    //Actionbara Settings yazdık.
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null

    }


}