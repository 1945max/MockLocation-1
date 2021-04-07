package com.mason.mocklocation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import java.util.*

class MyService:Service(){
    @SuppressLint("MissingPermission")
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        val mockLocation = Location(LocationManager.GPS_PROVIDER) // a string
        mockLocation.latitude = 30.587684
        mockLocation.longitude = 104.053697
        mockLocation.altitude = 10.0
        mockLocation.accuracy = 5.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
        Thread {
            while (true) {
//                if (null === LocationUtil.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)) {
//                    LocationUtil.init()
//                }
//                if ((getSystemService(Context.LOCATION_SERVICE) as LocationManager).getProvider(LocationManager.GPS_PROVIDER) === null) {
//                    LocationUtil.addGPS()
//                }
                (getSystemService(LOCATION_SERVICE) as LocationManager).setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
                if (null === (getSystemService(LOCATION_SERVICE) as LocationManager).getLastKnownLocation(LocationManager.GPS_PROVIDER)?.latitude) {
                    init()
                }
//                if (LocationUtil.locationManager.getProvider(LocationManager.NETWORK_PROVIDER) === null) {
//                    LocationUtil.addNET()
//                }
//                LocationUtil.locationManager.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, mockLocation)
//                Log.i("location_hash", "${LocationUtil.locationManager.hashCode()}")
                Log.i("system_location_hash", "${(getSystemService(LOCATION_SERVICE) as LocationManager).hashCode()}")
                Log.i("system_provider", "${(getSystemService(LOCATION_SERVICE) as LocationManager).getProvider(LocationManager.GPS_PROVIDER).hashCode()}")
                Log.i("test_info:", "${Date().time}:${(getSystemService(LOCATION_SERVICE) as LocationManager).getLastKnownLocation(LocationManager.GPS_PROVIDER)?.latitude}:${(getSystemService(LOCATION_SERVICE) as LocationManager).getLastKnownLocation(LocationManager.GPS_PROVIDER)?.longitude}")
                Thread.sleep(1000)
            }
        }.start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    @SuppressLint("MissingPermission")
    fun init() {
        if ((getSystemService(LOCATION_SERVICE) as LocationManager).getProvider(LocationManager.GPS_PROVIDER) != null) {
            try {
                (getSystemService(LOCATION_SERVICE) as LocationManager).removeTestProvider(LocationManager.GPS_PROVIDER)
            } catch (e: IllegalArgumentException) {

            } catch (e:AbstractMethodError) {

            }
        }
        (getSystemService(LOCATION_SERVICE) as LocationManager).addTestProvider(LocationManager.GPS_PROVIDER,
                false, true,
                false, false, true,
                true, true,
                Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
        (getSystemService(LOCATION_SERVICE) as LocationManager).setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
//        (getSystemService(LOCATION_SERVICE) as LocationManager).requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, LocationUtil.locationListener)
    }
}