package com.example.taxigofordriver.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.gms.location.FusedLocationProviderClient

import androidx.core.content.ContextCompat
import com.example.taxigofordriver.R


class LocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient,

) {
    interface LocationCallback {
        fun onLocationReceived(latitude: Double, longitude: Double)
        fun onPermissionDenied(message:Int)
    }

    fun checkAndRequestLocationPermission(activity: Activity, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                requestCode
            )
        }
    }

    fun getLastKnownLocation(callback: LocationCallback) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    callback.onLocationReceived(it.latitude, it.longitude)
                } ?: callback.onPermissionDenied(R.string.location_access_denied)
            }
        } else {
            callback.onPermissionDenied(R.string.location_access_denied)
        }
    }
}
