package com.example.weatherstate.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherstate.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE = 1

class MainActivity : AppCompatActivity(), KodeinAware{

    override val kodein by closestKodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }



    private lateinit var navController: NavController

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

        requestLocationPermission()

        if (hasLocationPermission()){
            bindLocationManager()
        }else{
            requestLocationPermission()
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
                this,
                fusedLocationProviderClient,
                locationCallback
        )

    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    private fun requestLocationPermission() {
       ActivityCompat.requestPermissions(
               this,
               arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
               MY_PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE

       )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ){
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE){
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                Toast.makeText(this, R.string.toastMessage, Toast.LENGTH_SHORT).show()
        }
    }


}