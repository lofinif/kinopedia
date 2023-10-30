package com.example.kinopedia.ui.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentSearchBinding
import com.example.kinopedia.ui.search.viewmodel.SearchViewModel

class SearchFragment : Fragment(), NavigationActionListener {

    private val sharedViewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 30, 0, 0)
    private val adapter = SearchAdapter(this)
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
        sharedViewModel.topFilms.observe(viewLifecycleOwner) {
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
}
