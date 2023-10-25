package com.example.kinopedia.ui.cinema.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentCinemaWelcomeBinding
import com.example.kinopedia.network.services.LoadingStatus
import com.example.kinopedia.ui.cinema.viewmodel.NearestCinemaViewModel
import com.example.kinopedia.utils.LocationProvider


class CinemaWelcomeFragment : Fragment() {
    private lateinit var binding: FragmentCinemaWelcomeBinding
    val sharedViewModel: NearestCinemaViewModel by activityViewModels()
    val adapter = CinemaAdapter()
    val bundle = Bundle()
    private val locationProvider by lazy { LocationProvider(requireContext(), ::onLocationReceived) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaWelcomeBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        checkPermissions()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bind(){
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            recyclerViewCinemas.adapter = adapter
            map.isVisible = false
            map.setOnClickListener {
                findNavController().navigate(R.id.action_cinemaWelcomeFragment_to_nearestCinemaFragment, bundle)
            }
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        sharedViewModel.apply {
            status.observe(viewLifecycleOwner) {
                if (status.value == LoadingStatus.DONE) {
                    binding.map.isVisible = true
                }
            }
            city.observe(viewLifecycleOwner) {
                binding.currentCity.text = it.address.displayCity.toString()
                getCinemas()
            }
            cinemas.observe(viewLifecycleOwner) { it ->
                val filteredElements = it.elements.filter {
                    it.tags?.name != null && it.tags.street != null && it.tags.housenumber != null
                }
                adapter.submitList(filteredElements)
            }
        }
    }
    private fun onLocationReceived(location: Location) {
        sharedViewModel.getCity(location.latitude, location.longitude)
        bundle.putDouble("latitude", location.latitude)
        bundle.putDouble("longitude", location.longitude)
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationProvider.requestLocationUpdates()
                } else {
                    Toast.makeText(requireContext(), "Нет разрешения на использование GPS", Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkPermissions() {
        if (hasLocationPermissions()) {
            locationProvider.requestLocationUpdates()
        } else {
            requestLocationPermissions()
        }
    }
    private fun hasLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestLocationPermissions() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 100)
    }
}