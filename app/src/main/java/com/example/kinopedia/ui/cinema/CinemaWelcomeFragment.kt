package com.example.kinopedia.ui.cinema

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
import com.example.kinopedia.network.LoadingStatus


class CinemaWelcomeFragment : Fragment() {
    private lateinit var binding: FragmentCinemaWelcomeBinding
    val sharedViewModel: NearestCinemaViewModel by activityViewModels()
    val adapter = CinemaAdapter()
    var latitude = 0.0
    var longitude = 0.0
    private var currentCity = ""
    var data = ""

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
                findNavController().navigate(R.id.action_cinemaWelcomeFragment_to_nearestCinemaFragment)
            }
            backButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        sharedViewModel.apply {
            status.observe(viewLifecycleOwner) {
                if (status.value == LoadingStatus.DONE) {
                    binding.map.isVisible = true
                }
            }
            city.observe(viewLifecycleOwner) {
                currentCity = it.address.displayCity.toString()
                Log.e("city", currentCity)
                binding.currentCity.text = currentCity
                data =
                    "[out:json];area[name=\"$currentCity\"](around:1000.0);nwr[amenity=cinema](area);out geom;"
                getCinemas(data)
            }
            cinemas.observe(viewLifecycleOwner) { it ->
                val filteredElements = it.elements.filter {
                    it.tags?.name != null && it.tags.street != null && it.tags.housenumber != null
                }
                adapter.submitList(filteredElements)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 100 && grantResults[0]  == PackageManager.PERMISSION_GRANTED){
            checkPermissions()
        } else {
            Toast.makeText(requireContext(), "Нет разрешения на использование GPS", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkPermissions(){
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
           requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 100)
        } else  {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                60,
                100f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        latitude = location.latitude
                        longitude = location.longitude
                        sharedViewModel.getCity(latitude, longitude)
                        locationManager.removeUpdates(this)
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                })
        }

    }
}