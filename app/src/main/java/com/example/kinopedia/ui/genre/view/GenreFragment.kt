package com.example.kinopedia.ui.genre.view

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
import com.example.kinopedia.databinding.FragmentGenreBinding
import com.example.kinopedia.ui.genre.viewmodel.GenreViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.utils.OnRetryClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GenreFragment : Fragment(), NavigationActionListener, OnRetryClickListener {

    private val sharedViewModel: GenreViewModel by viewModels()
    private lateinit var binding: FragmentGenreBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)
    private val adapter = GenreAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = GenreFragmentArgs.fromBundle(requireArguments())
        binding = FragmentGenreBinding.inflate(inflater)
        sharedViewModel.genreId = args.genreId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun genre() {
        when (GenreFragmentArgs.fromBundle(requireArguments()).genre) {
            "drama" -> binding.titleToolbarGenre.text = getString(R.string.collection_drama)
            "thriller" -> binding.titleToolbarGenre.text = getString(R.string.collection_thriller)
            "comedy" -> binding.titleToolbarGenre.text = getString(R.string.collection_comedy)
            "horror" -> binding.titleToolbarGenre.text = getString(R.string.collection_horror)
            "fantasy" -> binding.titleToolbarGenre.text = getString(R.string.collection_fantasy)
            "detective" -> binding.titleToolbarGenre.text = getString(R.string.collection_detective)

        }
    }

    private fun observerViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.flowGenre.collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                binding.genreError.root.isVisible = it.refresh is LoadState.Error
                binding.genreLoading.root.isVisible = it.refresh is LoadState.Loading
            }
        }
    }

    private fun bind() {
        genre()
        observerViewModel()
        binding.recyclerViewGenre.adapter = adapter.withLoadStateFooter(GenreLoadingAdapter(this))
        binding.apply {
            backButton.setOnClickListener { findNavController().popBackStack() }
            recyclerViewGenre.addItemDecoration(itemOffsetDecoration)
            genreError.tryAgain.setOnClickListener { adapter.refresh() }
        }

    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_genreFragment_to_filmPageFragment, bundle)
    }

    override fun retryLoading() {
        adapter.retry()
    }
}