package com.example.kinopedia.ui.search

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.ItemOffsetDecoration
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentSearchBinding
import com.example.kinopedia.ui.filter.FilterFragment
import com.example.kinopedia.ui.genre.GenreFragment

class SearchFragment : Fragment() {

    private val sharedViewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 30, 0, 0)
    private val adapter = SearchAdapter()
    private val bundleGenre = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        bind()
        return binding.root
    }
    private fun bind() {
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = this
        sharedViewModel.getTopFilms()
        sharedViewModel.dataTopFilms.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerViewSearchTop.addItemDecoration(itemOffsetDecoration)
            recyclerViewSearchTop.adapter = adapter

            buttonGenreThriller.setOnClickListener {
                bundleGenre.putString("genre", "thriller")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }
            buttonGenreDrama.setOnClickListener {
                bundleGenre.putString("genre", "drama")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }
            buttonGenreComedy.setOnClickListener {
                bundleGenre.putString("genre", "comedy")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }
            buttonGenreDetective.setOnClickListener {
                bundleGenre.putString("genre", "detective")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }
            buttonGenreFantasy.setOnClickListener {
                bundleGenre.putString("genre", "fantasy")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }
            buttonGenreHorror.setOnClickListener {
                bundleGenre.putString("genre", "horror")
                findNavController().navigate(
                    R.id.action_navigation_search_to_genreFragment, bundleGenre)
            }

            searchButton.setOnClickListener {
                    MAIN.navController.navigate(R.id.action_navigation_search_to_searchResultFragment)

            }
            buttonFilters.setOnClickListener {
                MAIN.navController.navigate(R.id.action_navigation_search_to_filterFragment)
            }
        }
    }
}
