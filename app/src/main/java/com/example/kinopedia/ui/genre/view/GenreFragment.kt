package com.example.kinopedia.ui.genre.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentGenreBinding
import com.example.kinopedia.ui.genre.viewmodel.GenreViewModel


class GenreFragment : Fragment(), NavigationActionListener {

    private val sharedViewModel: GenreViewModel by viewModels()
    private lateinit var binding: FragmentGenreBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)
    private val adapter = GenreAdapter(this)
    private var isLoaded = false
    private var genre = arrayOf(0)
    private var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun drama() {
        binding.titleToolbarGenre.text = "Драмы"
        genre = arrayOf(2)
        sharedViewModel.getFilmsByFiler(
                null,
                genre,
                "NUM_VOTE",
                "FILM",
                "",
                6,
                10,
                2020,
                2023,
                page
            )
            sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

        
    }
    private fun comedy(){
        binding.titleToolbarGenre.text = "Комедии"
        genre = arrayOf(13)
        sharedViewModel.getFilmsByFiler(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun fantasy(){
        binding.titleToolbarGenre.text = "Фэнтези"
        genre = arrayOf(12)
        sharedViewModel.getFilmsByFiler(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun detective(){
        binding.titleToolbarGenre.text = "Детективы"
        genre = arrayOf(5)
        sharedViewModel.getFilmsByFiler(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun thriller(){
        binding.titleToolbarGenre.text = "Триллеры"
        genre = arrayOf(1)
        sharedViewModel.getFilmsByFiler(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun horror(){
        binding.titleToolbarGenre.text = "Ужасы"
        genre = arrayOf(17)
        sharedViewModel.getFilmsByFiler(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    
    
    private fun bind(){
        binding.apply {
            recyclerViewGenre.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            recyclerViewGenre.addOnScrollListener(listener)
            recyclerViewGenre.addItemDecoration(itemOffsetDecoration)
        }
        when (arguments?.getString("genre")) {
            "drama" -> drama()
            "comedy" -> comedy()
            "fantasy" -> fantasy()
            "detective" -> detective()
            "thriller" -> thriller()
            "horror" -> horror()
            else -> {Log.i("unknown genre", "unknown genre")}
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadNextItems(page: Int) {
        sharedViewModel.loadNextItems(
            null,
            genre,
            "NUM_VOTE",
            "FILM",
            "",
            6,
            10,
            2020,
            2023,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            binding.recyclerViewGenre.post{
                sharedViewModel.dataFilmsByFilter.value?.let { it1 -> adapter.addAll(it1) }
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            isLoaded = false
        }, 500)
    }

    private val listener = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (!isLoaded) {
                if (lastVisibleItemPosition == totalItemCount - 1 && page < sharedViewModel.pageCount) {
                    isLoaded = true
                    page++
                    loadNextItems(page)
                }
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_genreFragment_to_filmPageFragment, bundle)
    }
}