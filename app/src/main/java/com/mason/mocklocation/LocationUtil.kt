package com.mason.mocklocation

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.os.RemoteException

class LocationUtil {
    companion object {
        lateinit var locationManager:LocationManager
        lateinit var locationListener:LocationListener


        fun init() {
            if (null === locationManager) {
                return
            }
            addGPS()
            addNET()
        }

        @SuppressLint("MissingPermission")
        fun addGPS() {
            if (null === locationManager) {
                return
            }
            if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
                try {
                    locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
                } catch (e: IllegalArgumentException) {

                }
            }
            locationManager.addTestProvider(LocationManager.GPS_PROVIDER,
                    false, true,
                    false, false, true,
                    true, true,
                    Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
            locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }

        @SuppressLint("MissingPermission")
        fun addNET() {
            if (null === locationManager) {
                return
            }
            if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
                try {
                    locationManager.removeTestProvider(LocationManager.NETWORK_PROVIDER)
                } catch (e:RemoteException) {

                } catch (e:IllegalArgumentException) {

                }
            }
            locationManager.addTestProvider(LocationManager.NETWORK_PROVIDER,
                    false, true,
                    false, false, true,
                    true, true,
                    Criteria.POWER_LOW, Criteria.ACCURACY_FINE)
            locationManager.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER, true)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0f, locationListener)
        }
    }
}