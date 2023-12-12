package com.example.kinopedia.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.R
import com.example.kinopedia.databinding.LayoutFilmItemLoadingVerticalBinding
import com.example.kinopedia.utils.OnRetryClickListener

class SearchResultLoadingAdapter(private val onRetryClickListener: OnRetryClickListener) :
    LoadStateAdapter<AdapterResultViewHolder>() {
    override fun onBindViewHolder(holder: AdapterResultViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): AdapterResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_film_item_loading_vertical, parent, false)
        val binding = LayoutFilmItemLoadingVerticalBinding.bind(view)
        return AdapterResultViewHolder(binding, onRetryClickListener)
    }

}

class AdapterResultViewHolder(
    private val binding: LayoutFilmItemLoadingVerticalBinding,
    private val onRetryClickListener: OnRetryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.shimmer.root.isVisible = loadState is LoadState.Loading
        binding.shimmer1.root.isVisible = loadState is LoadState.Loading
        binding.shimmer3.root.isVisible = loadState is LoadState.Loading
        binding.shimmer2.root.isVisible = loadState is LoadState.Loading
        binding.error.root.isVisible = loadState is LoadState.Error
        binding.error.tryAgain.setOnClickListener { onRetryClickListener.retryLoading() }
    }
}