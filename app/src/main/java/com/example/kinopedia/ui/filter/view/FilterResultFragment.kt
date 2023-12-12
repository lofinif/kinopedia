package com.example.kinopedia.ui.filter.view

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.kinopedia.databinding.FragmentFilterResultBinding
import com.example.kinopedia.ui.filter.viewmodel.FilterViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterResultFragment : Fragment(), NavigationActionListener {
    private lateinit var binding: FragmentFilterResultBinding
    private val sharedViewModel: FilterViewModel by activityViewModels()
    private val adapter = FilterResultAdapter(this)
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        observeViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.flowFilters.collectLatest {
                adapter.submitData(it)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.loadStateFlow.collect {
                    binding.listError.root.isVisible = it.refresh is LoadState.Error
                    binding.listLoading.root.isVisible = it.refresh is LoadState.Loading
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bind() {
        binding.apply {
            recyclerViewFilterResult.adapter = adapter.withLoadStateFooter(
                FilterResultLoadingAdapter()
            )
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            backButton.setOnClickListener { findNavController().popBackStack() }
            recyclerViewFilterResult.addItemDecoration(itemOffsetDecoration)
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_filterResultFragment_to_filmPageFragment, bundle
        )
    }
}

