package com.example.kinopedia.ui.more.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentMoreBinding
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.ui.more.adapter.MoreAdapter
import com.example.kinopedia.ui.more.adapter.MoreLoadingAdapter
import com.example.kinopedia.ui.more.viewmodel.MoreViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.utils.OnRetryClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment : Fragment(), NavigationActionListener, OnRetryClickListener {
    private val sharedViewModel: MoreViewModel by viewModels()
    private lateinit var binding: FragmentMoreBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)
    private val adapterAwait = MoreAdapter<FilmForAdapterModel>(this)
    private val adapterPremier = MoreAdapter<ThisMonthFilmModel>(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()
    }

    private fun observeViewModelPopular() {
        binding.moreToolbarTitle.text = getString(R.string.trending)
        binding.recyclerViewMore.adapter =
            adapterAwait.withLoadStateFooter(MoreLoadingAdapter(this))
        viewLifecycleOwner.lifecycleScope.launch {
            adapterAwait.loadStateFlow.collect {
                binding.listError.root.isVisible = it.refresh is LoadState.Error
                binding.listLoading.root.isVisible = it.refresh is LoadState.Loading
                binding.listError.tryAgain.setOnClickListener { adapterAwait.refresh() }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.popularFlow.collectLatest { pagingData ->
                adapterAwait.submitData(pagingData)
            }
        }
    }

    private fun observeViewModelComingSoon() {
        binding.moreToolbarTitle.text = getString(R.string.coming_soon)
        binding.recyclerViewMore.adapter =
            adapterAwait.withLoadStateFooter(MoreLoadingAdapter(this))
        viewLifecycleOwner.lifecycleScope.launch {
            adapterAwait.loadStateFlow.collect {
                binding.listError.root.isVisible = it.refresh is LoadState.Error
                binding.listLoading.root.isVisible = it.refresh is LoadState.Loading
                binding.listError.tryAgain.setOnClickListener { adapterAwait.refresh() }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.awaitFlow.collectLatest { pagingData ->
                adapterAwait.submitData(pagingData)
            }
        }
    }

    private fun observeViewModelPremier() {
        binding.moreToolbarTitle.text = getString(R.string.premiers)
        binding.recyclerViewMore.adapter =
            adapterPremier.withLoadStateFooter(MoreLoadingAdapter(this))
        viewLifecycleOwner.lifecycleScope.launch {
            adapterPremier.loadStateFlow.collect {
                binding.listError.root.isVisible = it.refresh is LoadState.Error
                binding.listLoading.root.isVisible = it.refresh is LoadState.Loading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.premierFlow.collectLatest { pagingData ->
                adapterPremier.submitData(pagingData)
            }
        }
    }

    private fun bind() {
        binding.apply {
            backButton.setOnClickListener { findNavController().popBackStack() }
            recyclerViewMore.addItemDecoration(itemOffsetDecoration)
            listError.tryAgain.setOnClickListener { adapterPremier.refresh() }
        }
        when (arguments?.getString("more_type")) {
            "Coming soon" -> observeViewModelComingSoon()
            "Trending" -> observeViewModelPopular()
            "Coming this month" -> observeViewModelPremier()
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_moreFragment_to_filmPageFragment,
            bundle
        )
    }

    override fun retryLoading() {
        adapterAwait.retry()
        adapterPremier.retry()
    }
}