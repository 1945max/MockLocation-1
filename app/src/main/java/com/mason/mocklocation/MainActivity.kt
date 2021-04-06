package com.mason.mocklocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.IllegalArgumentException
import java.security.Permission
import java.security.Permissions

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager:LocationManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION), 1)
            return
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun setLocation() {

//        116.334276,39.943962
        if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
            try {
                locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
            } catch (e:IllegalArgumentException) {

            }
        }
//        if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
//            try {
//                locationManager.removeTestProvider(LocationManager.NETWORK_PROVIDER)
//            } catch (e:RemoteException) {
//
//            } catch (e:IllegalArgumentException) {
//
//            }
//        }
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER,
                false, true,
                false, false, true,
                true, true,
                Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
//        locationManager.addTestProvider(LocationManager.NETWORK_PROVIDER,
//                false, true,
//                false, false, true,
//                true, true,
//                Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
//        locationManager.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER, true)

        val mockLocation = Location(LocationManager.GPS_PROVIDER) // a string
//        mockLocation.latitude = 39.943962
//        mockLocation.longitude = 116.334276
        mockLocation.latitude = 30.587684
        mockLocation.longitude = 104.053697
        mockLocation.altitude = 100.0
        mockLocation.accuracy = 5.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0f, this)
//        locationManager.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, mockLocation)
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0f, this)
//        locationManager.setTestProviderLocation(LocationManager.PASSIVE_PROVIDER, mockLocation)
//        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0, 0f, this)
    }

    fun updateLocation(view: View) {
        val mockLocation = Location(LocationManager.GPS_PROVIDER) // a string
        mockLocation.latitude = 30.587684
        mockLocation.longitude = 104.053697
        mockLocation.altitude = 10.0
        mockLocation.accuracy = 5.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        Thread {
            while (true) {
//                mockLocation.time = System.currentTimeMillis()
//                if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
//                    try {
//                        locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
//                    } catch (e:IllegalArgumentException) {
//
//                    }
//                }
//                locationManager.addTestProvider(LocationManager.GPS_PROVIDER,
//                        false, true,
//                        false, false, true,
//                        true, true,
//                        Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
                setLocation()
//                locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
//                locationManager.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, mockLocation)
//                locationManager.setTestProviderLocation(LocationManager.PASSIVE_PROVIDER, mockLocation)
                Thread.sleep(50)
            }
        }.start()

    }

    override fun onLocationChanged(location: Location) {
        println("xxxxxx location: ${location.longitude}, ${location.latitude}")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show()
    }
}