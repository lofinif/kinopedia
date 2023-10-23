package com.example.kinopedia.ui.cinema

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentNearestCinemaBinding
import com.example.kinopedia.network.CinemaOSM
import org.osmdroid.api.IMapController
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

class NearestCinemaFragment : Fragment() {
    private lateinit var binding: FragmentNearestCinemaBinding
    private lateinit var mapView: MapView
    private lateinit var rotationGestureOverlay: RotationGestureOverlay
    private lateinit var mapController: IMapController
    private val sharedViewModel: NearestCinemaViewModel by activityViewModels()
    private var latitude = 0.0
    private var longitude = 0.0
    private var filteredElements = emptyList<CinemaOSM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNearestCinemaBinding.inflate(inflater)
        (activity as MainActivity).bottomNavigation.isVisible = false
        mapView = binding.mapview
        rotationGestureOverlay = RotationGestureOverlay(mapView)
        bind()
        requestLocationUpdates()
        createMap()
        markers()
        mapView.invalidate()
        return binding.root
    }

    private fun markers(){
        sharedViewModel.cinemas.observe(viewLifecycleOwner) { it ->
            filteredElements =
                it.elements.filter { it.tags?.name != null && it.tags.street != null && it.tags.housenumber != null}
            for (i in filteredElements) {
                val markerMap = Marker(mapView)
                if (i.latitude != null && i.longitude != null) {
                    markerMap.position = GeoPoint(i.latitude, i.longitude)
                    markerMap.title = i.tags?.name
                    markerMap.icon = ResourcesCompat.getDrawable(resources, R.drawable.baseline_location_on_24_blue, null)
                    markerMap.snippet = i.tags?.address
                    markerMap.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView.overlays.add(markerMap)
                    markerMap.setOnMarkerClickListener { marker, _ ->
                        binding.infoCinema.isVisible = true
                        binding.nameCinema.text = marker.title
                        binding.descriptionCinema.text = marker.snippet
                        true
                    }
                }
            }
        }
    }

    private fun createMap(){

        mapController = mapView.controller
        mapController.setZoom(14.5)
        mapView.setBuiltInZoomControls(false)

        rotationGestureOverlay.isEnabled
        mapView.setMultiTouchControls(true)
        mapView.overlays.add(rotationGestureOverlay)

        getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.isClickable = true
    }
    private fun bind(){
        binding.apply {
            infoCinema.isVisible = false
            backButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 100 && grantResults[0]  == PackageManager.PERMISSION_GRANTED){
            requestLocationUpdates()
        } else {
            Toast.makeText(requireContext(), "Нет разрешения на использование GPS", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

     private fun requestLocationUpdates() {
         val marker = Marker(mapView)
         marker.position = GeoPoint(latitude, longitude)
         marker.icon = ResourcesCompat.getDrawable(
             resources,
             R.drawable.baseline_my_location_24,
             null
         )
         val locationManager: LocationManager =
             requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
         if (ActivityCompat.checkSelfPermission(
                 requireContext(),
                 Manifest.permission.ACCESS_COARSE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
             && ActivityCompat.checkSelfPermission(
                 requireContext(),
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             requestPermissions(
                 arrayOf(
                     Manifest.permission.ACCESS_COARSE_LOCATION,
                     Manifest.permission.ACCESS_FINE_LOCATION
                 ), 100
             )
         } else {

             locationManager.requestLocationUpdates(
                 LocationManager.NETWORK_PROVIDER,
                 1,
                 0f,
                 object : LocationListener {
                     override fun onLocationChanged(location: Location) {
                         latitude = location.latitude
                         longitude = location.longitude
                         if (context != null) {
                             val startPoint = GeoPoint(latitude, longitude)
                             marker.position = GeoPoint(latitude, longitude)
                             marker.icon = ResourcesCompat.getDrawable(
                                 resources,
                                 R.drawable.baseline_my_location_24,
                                 null
                             )
                             marker.setOnMarkerClickListener { _, _ ->
                                 true
                             }
                             mapView.overlays.add(marker)
                             mapController.setCenter(startPoint)
                         }
                         locationManager.removeUpdates(this)
                     }

                     override fun onStatusChanged(
                         provider: String?,
                         status: Int,
                         extras: Bundle?
                     ) {
                     }
                 })
         }
     }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).bottomNavigation.isVisible = true
    }
}