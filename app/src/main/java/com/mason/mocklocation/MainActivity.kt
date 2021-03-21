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
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
//        116.334276,39.943962
        if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
            locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
        }
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER,
                false, true,
                false, false, true,
                true, true,
                Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)

        val mockLocation = Location(LocationManager.GPS_PROVIDER) // a string
        mockLocation.latitude = 39.943962
        mockLocation.longitude = 116.334276
        mockLocation.altitude = 100.0
        mockLocation.accuracy = 5.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0f, this)
    }

    fun updateLocation(view: View) {
        val mockLocation = Location(LocationManager.GPS_PROVIDER) // a string
        mockLocation.latitude = 39.936900
        mockLocation.longitude = 116.321558
        mockLocation.altitude = 10.0
        mockLocation.accuracy = 5.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation)
    }

    override fun onLocationChanged(location: Location) {
        println("xxxxxx location: ${location.longitude}, ${location.latitude}")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show()
    }
}