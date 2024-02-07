package com.example.kinopedia.ui.cinema.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentCinemaWelcomeBinding
import com.example.kinopedia.ui.cinema.state.CinemaScreenState
import com.example.kinopedia.ui.cinema.viewmodel.NearestCinemaViewModel
import com.example.kinopedia.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CinemaWelcomeFragment : Fragment() {
    private lateinit var binding: FragmentCinemaWelcomeBinding
    val sharedViewModel: NearestCinemaViewModel by activityViewModels()
    val adapter = CinemaAdapter()
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCinemaWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        checkPermissions()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bind() {
        EspressoIdlingResource.increment()
        sharedViewModel.setCallBack(::onLocationReceived)
        binding.cinemaContent.apply {
            recyclerViewCinemas.adapter = adapter
            map.setOnClickListener {
                findNavController().navigate(
                    R.id.action_cinemaWelcomeFragment_to_nearestCinemaFragment,
                    bundle
                )
            }
        }
        binding.cinemaContent.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        sharedViewModel.apply {
            sharedViewModel.screenState.observe(viewLifecycleOwner) {
                binding.cinemaLoading.root.isVisible = it is CinemaScreenState.Loading
                binding.cinemaError.root.isVisible = it is CinemaScreenState.Error
                binding.cinemaContent.root.isVisible = it is CinemaScreenState.Loaded
                if (it is CinemaScreenState.Loaded){
                    EspressoIdlingResource.decrement()
                }

            }
            city.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.cinemaContent.currentCity.text = it.address.displayCity.toString()
                }
            }
            cinemas.observe(viewLifecycleOwner) { it ->
                val filteredElements = it?.filter {
                    it.tags?.name != null && it.tags.street != null && it.tags.housenumber != null
                }
                adapter.submitList(filteredElements)
            }
        }
    }

    private fun onLocationReceived(location: Location) {
        if (sharedViewModel.city.value == null) {
            sharedViewModel.fetchCinemas(location.latitude, location.longitude)
        }
        bundle.putDouble(getString(R.string.latitude), location.latitude)
        bundle.putDouble(getString(R.string.longitude), location.longitude)
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
                    sharedViewModel.locationProvider.requestLocationUpdates()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_permission_to_use_gps),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkPermissions() {
        if (hasLocationPermissions()) {
            sharedViewModel.locationProvider.requestLocationUpdates()
        } else {
            requestLocationPermissions()
        }
    }

    private fun hasLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 100
        )
    }
}