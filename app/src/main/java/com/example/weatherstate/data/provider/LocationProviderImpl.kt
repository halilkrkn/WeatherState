package com.example.weatherstate.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.weatherstate.data.db.entity.WeatherLocation
import com.example.weatherstate.internal.LocationPermissionNotGrantedExpetion
import com.example.weatherstate.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred


const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION" //cihazın lokasyonu kullanılır.
const val CUSTOM_LOCATION = "CUSTOM_LOCATION" // özel lokasyon değiştilebilir lokasyon

class LocationProviderImpl(
        private val fusedLocationProviderClient: FusedLocationProviderClient, // GoogleMap den güncel lokasyon bilgilerini çekiyoruz.
        context: Context
) :PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext


    // TODO: ******** getPreferredLocationString() fonksiyonu LocationProvider dan  implement ettik*********
    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                        ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            } catch (e:LocationPermissionNotGrantedExpetion){
                return "${getCustomLocationName()}"
            }

        }else{
            return "${getCustomLocationName()}"
        }
    }

    // TODO: ******** hasLocationChanged fonksiyonu LocationProvider dan  implement ettik*********
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }catch (e:LocationPermissionNotGrantedExpetion){
            false
        }
        val customLocationChanged =  hasCustomLocationChanged(lastWeatherLocation)
        return deviceLocationChanged || customLocationChanged
    }
    
    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
               ?: return false

//        Comparing doubles cannot be done with "==" => Çiftleri karşılaştırma "==" ile yapılamaz
        val comparisonTresHold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonTresHold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonTresHold

    }

    private  fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION,null)
    }


    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION,true)
    }


    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedExpetion()
    }

    private  fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }



    
}