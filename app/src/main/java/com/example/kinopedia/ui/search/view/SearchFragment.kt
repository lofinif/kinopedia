package com.example.kinopedia.ui.search.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentSearchBinding
import com.example.kinopedia.ui.search.adapter.SearchAdapter
import com.example.kinopedia.ui.search.adapter.SearchLoadingAdapter
import com.example.kinopedia.ui.search.viewmodel.SearchViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.utils.OnRetryClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), NavigationActionListener, OnRetryClickListener {

    private val sharedViewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 30, 0, 0)
    private val adapter = SearchAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater)
        bind()
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchError.tryAgain.setOnClickListener {
            adapter.refresh()
            Log.e("SearchFragment", "try again")
        }
    }

    private fun observeViewModel() {
        binding.searchContent.recyclerViewSearchTop.adapter = adapter.withLoadStateFooter(
            SearchLoadingAdapter(this)
        )
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.flowTop.collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                Log.e("searchTest", "state0")
                binding.searchError.root.isVisible = it.refresh is LoadState.Error
                binding.searchLoading.root.isVisible = it.refresh is LoadState.Loading
                binding.searchContent.root.isVisible = it.refresh is LoadState.NotLoading
            }
        }
    }

    private fun bind() {
        binding.searchContent.viewModel = sharedViewModel
        binding.searchContent.lifecycleOwner = this

        binding.searchContent.apply {
            recyclerViewSearchTop.addItemDecoration(itemOffsetDecoration)
            buttonGenreThriller.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("thriller", 1)
                findNavController().navigate(action)
            }

            buttonGenreDrama.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("drama", 2)
                findNavController().navigate(action)
            }

            buttonGenreComedy.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("comedy", 13)
                findNavController().navigate(action)
            }
            buttonGenreDetective.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("detective", 5)
                findNavController().navigate(action)
            }
            buttonGenreFantasy.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("fantasy", 12)
                findNavController().navigate(action)
            }
            buttonGenreHorror.setOnClickListener {
                val action =
                    SearchFragmentDirections.actionNavigationSearchToGenreFragment("horror", 17)
                findNavController().navigate(action)
            }

            searchButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_search_to_searchResultFragment)

            }
            buttonFilters.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_search_to_filterFragment)
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_navigation_search_to_filmPageFragment, bundle)
    }

    override fun retryLoading() {
        adapter.retry()
    }
}
