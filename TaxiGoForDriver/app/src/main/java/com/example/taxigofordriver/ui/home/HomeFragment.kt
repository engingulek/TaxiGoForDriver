package com.example.taxigofordriver.ui.home

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.tasks.Task
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import org.junit.runner.manipulation.Ordering


class HomeFragment : Fragment() {
   private  var controlCheck = 0

    private lateinit var mapView:MapView
    private lateinit var design:FragmentHomeBinding
    private lateinit var flpc:FusedLocationProviderClient
    private lateinit var locationTask:Task<Location>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        flpc = LocationServices.getFusedLocationProviderClient(requireContext())

        controlCheck = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {


            locationTask = flpc.lastLocation
            locationTask.addOnSuccessListener {


                getLocation(it.latitude,it.longitude)
            }

        } else {

            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                100
            )
        }

        mapView = design.yandexMapId



        return design.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        MapKitFactory.initialize(requireContext())
    }

    private fun getLocation(latitude:Double,longitude:Double){

        val cameraPosition =
            com.yandex.mapkit.map.CameraPosition(Point(latitude, longitude), 15.0f, 30.0f, 30.0f)
        mapView.map.move(cameraPosition)

        val mapObjects = mapView.map.mapObjects
        mapObjects.addPlacemark(Point(latitude, longitude))

    }


}