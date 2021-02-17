package com.example.weatherstate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

//LifecycleBoundLocationManager = Yaşam döngüsüne bağlı lokasyon yöneticisi
class LifecycleBoundLocationManager(
        lifecycleOwner: LifecycleOwner,
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        private val locationCallback: LocationCallback
): LifecycleObserver {
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply{
        interval = 5000
        fastestInterval = 10 * 60 * 1000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}