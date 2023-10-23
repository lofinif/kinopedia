package com.example.kinopedia.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.NavigationActionListener
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment(), NavigationActionListener {

    private val sharedViewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchResultBinding
    private var adapter = SearchResultAdapter(this)
    private var page = 1
    private var keyWord = ""
    private var isLoaded = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        if (sharedViewModel.dataFilmsByFilter.value.isNullOrEmpty()){
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
           binding.searchButton.requestFocus()
            inputMethodManager.showSoftInput(binding.searchButton, InputMethodManager.SHOW_IMPLICIT)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        search()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.right = 30
                outRect.top = 30
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun bind() {
      val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel
        binding.recyclerViewSearch.addOnScrollListener(listener)
        binding.backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.apply {
            recyclerViewSearch.adapter = adapter
            addItemDecoration(recyclerViewSearch)
            searchButton.isIconified = false
        }
      binding.recyclerViewSearch.setOnTouchListener(

         (View.OnTouchListener { v, event ->
                v.performClick()
                if(inputMethodManager.isActive){
                    inputMethodManager.hideSoftInputFromWindow(binding.constraintLayout.windowToken, 0)
                }
                false
            })
        )
    }

    private fun search() {
        if (!sharedViewModel.dataFilmsByFilter.value.isNullOrEmpty()){
            binding.searchButton.clearFocus()
        }
        binding.searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    keyWord = query
                    sharedViewModel.getFilmsByKeyWord(
                        null,
                        null,
                        "NUM_VOTE",
                        "FILM",
                        query,
                        0,
                        10,
                        1800,
                        9999,
                        1
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        binding.searchButton.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(view, 0)
            }
        }
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
    }
    private fun loadNextItems(page: Int) {
        sharedViewModel.loadNextItems(
            null,
            null,
            "NUM_VOTE",
            "FILM",
            keyWord,
            0,
            10,
            1800,
            9999,
            page
        )
        sharedViewModel.dataFilmsByFilter.observe(viewLifecycleOwner) {
            binding.recyclerViewSearch.post {
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
                if (lastVisibleItemPosition == totalItemCount - 5 && page < sharedViewModel.pageCount) {
                    isLoaded = true
                    page++
                    loadNextItems(page)
                    Log.e("12", "12")
                }
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_searchResultFragment_to_filmPageFragment, bundle)
    }
}
