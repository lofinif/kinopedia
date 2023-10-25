package com.example.kinopedia.ui.cinema.view

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
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentNearestCinemaBinding
import com.example.kinopedia.network.models.CinemaOSM
import com.example.kinopedia.ui.cinema.viewmodel.NearestCinemaViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

class NearestCinemaFragment : Fragment() {
    private lateinit var binding: FragmentNearestCinemaBinding
    private val mapView by lazy { binding.mapview }
    private val rotationGestureOverlay by lazy { RotationGestureOverlay(mapView) }
    private val mapController by lazy { mapView.controller }
    private val sharedViewModel: NearestCinemaViewModel by activityViewModels()
    private var latitude = 0.0
    private var longitude = 0.0
    private var filteredElements = emptyList<CinemaOSM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNearestCinemaBinding.inflate(inflater)
        arguments?.let {
            latitude = it.getDouble("latitude", 0.0)
            longitude = it.getDouble("longitude", 0.0)
        }
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        bind()
        configureMap()
        displayUserLocation()
        observeCinemas()
    }

    private fun bind() {
        binding.apply {
            infoCinema.isVisible = false
            backButton.setOnClickListener { findNavController().popBackStack() }
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun configureMap() {
        mapController.setZoom(14.5)
        mapView.apply {
            setBuiltInZoomControls(false)
            rotationGestureOverlay.isEnabled = true
            setMultiTouchControls(true)
            overlays.add(rotationGestureOverlay)
            setTileSource(TileSourceFactory.MAPNIK)
            isClickable = true
        }
    }

    private fun displayUserLocation() {
        mapView.overlays.add(createUserMarker())
        mapController.setCenter(GeoPoint(latitude, longitude))
    }

    private fun createUserMarker(): Marker {
        return Marker(mapView).apply {
            position = GeoPoint(latitude, longitude)
            icon = ResourcesCompat.getDrawable(resources, R.drawable.baseline_my_location_24, null)
            setOnMarkerClickListener { _, _ -> true }
        }
    }

    private fun observeCinemas() {
        sharedViewModel.cinemas.observe(viewLifecycleOwner) { it ->
            filteredElements = it.elements.filterNotNull()
                .filter { cinema ->
                    cinema.tags?.name != null && cinema.tags.street != null && cinema.tags.housenumber != null
                }
            displayCinemaMarkers()
        }
    }

    private fun displayCinemaMarkers() {
        filteredElements.forEach { cinema ->
            mapView.overlays.add(createCinemaMarker(cinema))
        }
    }

    private fun createCinemaMarker(cinema: CinemaOSM): Marker {
        return Marker(mapView).apply {
            cinema.latitude?.let { lat ->
                cinema.longitude?.let { lon ->
                    position = GeoPoint(lat, lon)
                    title = cinema.tags?.name
                    snippet = cinema.tags?.address
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.baseline_location_on_24_blue, null)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    setOnMarkerClickListener { marker, _ ->
                        binding.apply {
                            infoCinema.isVisible = true
                            nameCinema.text = marker.title
                            descriptionCinema.text = marker.snippet
                        }
                        true
                    }
                }
            }
        }
    }
}