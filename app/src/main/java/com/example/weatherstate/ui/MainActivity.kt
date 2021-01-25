package com.example.weatherstate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherstate.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //setSupportActionBar(toolbar)hatırlamak

        //Gezinme Denetleyicisini Bulma
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // BottomNavigationView ile Gezinme Denetleyicisini Ayarlama işlemi yapıldı.
        bottom_nav.setupWithNavController(navController)
        // ActionBar'ı Gezinme Denetleyicisi ile Ayarlama işlemi yapıldı
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}