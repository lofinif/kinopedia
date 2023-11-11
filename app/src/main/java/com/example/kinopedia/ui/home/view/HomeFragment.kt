package com.example.kinopedia.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentHomeBinding
import com.example.kinopedia.ui.home.viewmodel.HomeViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), NavigationActionListener {

    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private val homeViewPagerAdapter = HomeViewPagerAdapter(this)
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 60, 0, 0)
    private val itemOffsetDecorationViewPager = ItemOffsetDecoration(30, 30, 0, 0)
    private val homeAdapterTrending = HomeAdapter(this)
    private val homeAdapterComingSoon = HomeAdapter(this)
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

    private fun observeViewModel(){
        viewLifecycleOwner.lifecycleScope.launch() {
            sharedViewModel.flowComingSoon.collectLatest {
                homeAdapterComingSoon.submitList(it)
            }
        }
            viewLifecycleOwner.lifecycleScope.launch()  {
            sharedViewModel.flowAwaitFilms.collectLatest {
                homeAdapterTrending.submitList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch()  {
            sharedViewModel.flowPremiers.collectLatest {
                homeViewPagerAdapter.addAllComingThisMonth(it)
            }
        }
    }

    private fun bind() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
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
/*        sharedViewModel.apply {
            getAwaitFilms()
            getPopularFilms()
            getFilmsThisMonth()
            thisMonth.observe(viewLifecycleOwner) {
                homeViewPagerAdapter.addAllComingThisMonth(it)
            }
        }*/
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_navigation_home_to_filmPageFragment, bundle)
    }
}