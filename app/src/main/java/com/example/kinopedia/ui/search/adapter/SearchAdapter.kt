package com.example.kinopedia.ui.search.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilterTopItemBinding
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.utils.NavigationActionListener
import com.squareup.picasso.Picasso

class SearchAdapter(private val navigation: NavigationActionListener) :
    PagingDataAdapter<FilmForAdapterModel, SearchAdapter.SearchViewHolder>(
        Comparator()
    ) {

    class SearchViewHolder(private var binding: FilterTopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(filmForAdapter: FilmForAdapterModel, navigation: NavigationActionListener) {
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.posterTop)
            binding.nameMovie.text = filmForAdapter.displayName
            binding.genreMovie.text = filmForAdapter.displayGenre
            binding.posterTop.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<FilmForAdapterModel>() {
        override fun areItemsTheSame(
            oldItem: FilmForAdapterModel,
            newItem: FilmForAdapterModel
        ): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(
            oldItem: FilmForAdapterModel,
            newItem: FilmForAdapterModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            FilterTopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, navigation) }
    }

}