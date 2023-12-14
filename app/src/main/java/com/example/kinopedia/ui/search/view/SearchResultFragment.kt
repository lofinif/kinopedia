package com.example.kinopedia.ui.search.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentSearchResultBinding
import com.example.kinopedia.ui.search.adapter.SearchResultAdapter
import com.example.kinopedia.ui.search.adapter.SearchResultLoadingAdapter
import com.example.kinopedia.ui.search.viewmodel.SearchViewModel
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.utils.OnRetryClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment(), NavigationActionListener, OnRetryClickListener {

    private val sharedViewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchResultBinding
    private var adapter = SearchResultAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar

        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        observeViewModel()
        search()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.flowKeyWord.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                binding.searchResultError.root.isVisible = it.refresh is LoadState.Error
                binding.searchResultLoading.root.isVisible = it.refresh is LoadState.Loading
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun bind() {
        binding.searchButtonSearchResult.clearFocus()
        binding.recyclerViewSearch.adapter = adapter.withLoadStateFooter(
            SearchResultLoadingAdapter(this)
        )
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        binding.apply {
            addItemDecoration(recyclerViewSearch)
            searchButtonSearchResult.isIconified = false
        }
        binding.recyclerViewSearch.setOnTouchListener(
            (View.OnTouchListener { v, event ->
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                v.performClick()
                if (inputMethodManager.isActive) {
                    inputMethodManager.hideSoftInputFromWindow(
                        binding.constraintLayout.windowToken,
                        0
                    )
                }
                false
            })
        )
    }

    private fun search() {
        binding.searchButtonSearchResult.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    sharedViewModel.updateKeyWord(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        binding.searchButtonSearchResult.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                if (binding.searchButtonSearchResult.query.isNullOrEmpty()) {
                    val imm =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.showSoftInput(view, 0)
                }
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_searchResultFragment_to_filmPageFragment, bundle)
    }

    override fun retryLoading() {
        adapter.retry()
    }
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

