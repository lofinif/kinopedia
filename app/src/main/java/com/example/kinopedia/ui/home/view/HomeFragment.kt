package com.example.kinopedia.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentHomeBinding
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.ui.home.state.HomeScreenState
import com.example.kinopedia.ui.home.viewmodel.HomeViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), NavigationActionListener {

    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private val homeViewPagerAdapter = HomeAdapter<ThisMonthFilmModel>(this)
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 60, 0, 0)
    private val itemOffsetDecorationViewPager = ItemOffsetDecoration(30, 30, 0, 0)
    private val homeAdapterTrending =
        HomeAdapter<com.example.kinopedia.ui.home.model.FilmForAdapterModel>(this)
    private val homeAdapterComingSoon =
        HomeAdapter<com.example.kinopedia.ui.home.model.FilmForAdapterModel>(this)
    private val bundle = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel() {
        sharedViewModel.comingSoon.observe(viewLifecycleOwner) {
            homeAdapterComingSoon.submitList(it)
        }
        sharedViewModel.premier.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.submitList(it)
        }
        sharedViewModel.await.observe(viewLifecycleOwner) {
            homeAdapterTrending.submitList(it)
        }

        sharedViewModel.screenState.observe(viewLifecycleOwner) { state ->
            binding.homeError.root.isVisible = state is HomeScreenState.Error
            binding.homeLoading.root.isVisible = state is HomeScreenState.Loading
            binding.homeContent.root.isVisible = state is HomeScreenState.Loaded
        }
    }

    private fun bind() {
        if (sharedViewModel.comingSoon.value.isNullOrEmpty()
            || sharedViewModel.premier.value.isNullOrEmpty()
            || sharedViewModel.await.value.isNullOrEmpty()
        ) {
            sharedViewModel.fetchLists()
        }

        binding.homeError.tryAgain.setOnClickListener { sharedViewModel.fetchLists() }

        binding.homeContent.apply {
            recyclerViewComingSoon.adapter = homeAdapterComingSoon
            recyclerViewTrending.adapter = homeAdapterTrending
            viewPager.adapter = homeViewPagerAdapter
            viewPager.addItemDecoration(itemOffsetDecorationViewPager)
            recyclerViewComingSoon.addItemDecoration(itemOffsetDecoration)
            recyclerViewTrending.addItemDecoration(itemOffsetDecoration)
            viewAllComingSoon.setOnClickListener {
                bundle.putString("more_type", "Coming soon")
                findNavController().navigate(R.id.action_navigation_home_to_moreFragment, bundle)
            }

            viewAllTrending.setOnClickListener {
                bundle.putString("more_type", "Trending")
                findNavController().navigate(R.id.action_navigation_home_to_moreFragment, bundle)
            }

            viewAllThisMonth.setOnClickListener {
                bundle.putString("more_type", "Coming this month")
                findNavController().navigate(R.id.action_navigation_home_to_moreFragment, bundle)
            }
            nearestCinemaButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_cinemaWelcomeFragment)
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_navigation_home_to_filmPageFragment, bundle)
    }
}