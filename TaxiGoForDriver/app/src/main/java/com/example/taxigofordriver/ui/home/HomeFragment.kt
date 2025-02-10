package com.example.taxigofordriver.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.taxigofordriver.R
import com.example.taxigofordriver.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView


class HomeFragment : Fragment(),LocationManager.LocationCallback {

    private lateinit var mapView:MapView
    private lateinit var design:FragmentHomeBinding
    private lateinit var flpc:FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var viewModel:HomeViewModelInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        flpc = LocationServices.getFusedLocationProviderClient(requireContext())
        locationManager = LocationManager(requireContext(),
            LocationServices.getFusedLocationProviderClient(requireContext()))
        locationManager.checkAndRequestLocationPermission(requireActivity(), 100)
        locationManager.getLastKnownLocation(this)

        mapView = design.yandexMapId


        viewModel.uiState.observe(viewLifecycleOwner){
            design.uiState = it
        }

        design.stateSwitchId.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAction(HomeContract.UIAction.onTappedStateSwiftch(isChecked))
        }

        return design.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("f07be095-63e3-49cf-9bd8-c99cef347595")
        MapKitFactory.initialize(requireContext())

        val tempViewModel : HomeViewModelInterface by viewModels<HomeViewModel>()
        viewModel = tempViewModel
    }

    override fun onLocationReceived(latitude: Double, longitude: Double) {
       val cameraPosition = com.yandex.mapkit.map.CameraPosition(Point(latitude, longitude),
           18.0f, 30.0f, 30.0f)
        mapView.map.move(cameraPosition)

        val mapObjects = mapView.map.mapObjects
        mapObjects.addPlacemark(Point(latitude, longitude))
    }

    override fun onPermissionDenied(message:Int) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
    }


}