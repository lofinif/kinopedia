package com.example.kinopedia.ui.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.ItemOffsetDecoration
import com.example.kinopedia.MAIN
import com.example.kinopedia.databinding.FragmentFilterResultBinding

class FilterResultFragment : Fragment() {
    private lateinit var binding: FragmentFilterResultBinding
    private val sharedViewModel: FilterViewModel by activityViewModels()
    private val adapter = FilterResultAdapter()
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)
    private var isLoaded = false
    private var page = 1
    private var countryId = -1
    private var genreId = -1
    private var sortType = "RATING"
    private var type = "ALL"
    private var minRating = 0
    private var maxRating = 10
    private var selectedYearFrom = -1
    private var selectedYearTo = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterResultBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bind(){
        MAIN.supportActionBar?.title = "Результаты поиска"
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            recyclerViewFilterResult.adapter = adapter
            recyclerViewFilterResult.addOnScrollListener(listener)
            backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            recyclerViewFilterResult.addItemDecoration(itemOffsetDecoration)
            sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }
        getArgs()

    }

    private fun getArgs(){
        countryId = arguments?.getInt("countryId")!!
        genreId = arguments?.getInt("genreId")!!
        sortType = arguments?.getString("sortType")!!
        type = arguments?.getString("type")!!
        minRating = arguments?.getInt("minRating")!!
        maxRating = arguments?.getInt("maxRating")!!
        selectedYearFrom = arguments?.getInt("selectedYearFrom")!!
        selectedYearTo = arguments?.getInt("selectedYearTo")!!
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNextItems(page: Int) {
        val countryIds = if (countryId == -1) null else arrayOf(countryId)
        val genreIds = if(genreId == -1) null else arrayOf(genreId)
        sharedViewModel.loadNextItems(
            countryIds,
            genreIds,
            sortType,
            type,
            "",
            minRating,
            maxRating,
            selectedYearFrom,
            selectedYearTo,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            sharedViewModel.dataFilmsByFilter.value?.let { it1 -> adapter.addAll(it1) }
            binding.recyclerViewFilterResult.post{
                adapter.notifyDataSetChanged()
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
                if (lastVisibleItemPosition == totalItemCount - 10 && page < sharedViewModel.pageCount) {
                    isLoaded = true
                    page++
                    loadNextItems(page)
                }
            }
        }
    }
}

