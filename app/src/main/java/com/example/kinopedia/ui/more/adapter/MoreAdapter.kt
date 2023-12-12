package com.example.kinopedia.ui.more.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.squareup.picasso.Picasso

class MoreAdapter<T : Any>(private val navigation: NavigationActionListener) : PagingDataAdapter<T, RecyclerView.ViewHolder>(
    DiffUtilCallBack()
) {

    companion object{
        const val TYPE_PREMIER = 0
        const val TYPE_AWAIT_AND_COMING = 1
    }
    class MoreViewHolderTrendingAndAwait(private var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(filmForAdapter: FilmForAdapterModel, navigation: NavigationActionListener) {
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.posterSearchResult)
            binding.ratingKinopoiskSearchResult.text = filmForAdapter.displayRatingKinopoisk
            binding.nameMovieSearchResult.text = filmForAdapter.displayName
            binding.nameMovieOriginalSearchResult.text = filmForAdapter.displayOriginalName
            binding.ratingImdbSearchResult.text = filmForAdapter.displayRatingImdb
            binding.descriptionYearSearchResult.text = filmForAdapter.displayYear
            binding.descriptionGenreSearchResult.text = filmForAdapter.displayGenre
            binding.descriptionCountrySearchResult.text = filmForAdapter.displayCountry
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    class MoreViewHolderComingThisMonth(private var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(film: ThisMonthFilmModel, navigation: NavigationActionListener) {
            Picasso.get().load(film.posterUrl).into(binding.posterSearchResult)
            binding.ratingImdbSearchResult.text = film.dash
            binding.ratingKinopoiskSearchResult.text = film.dash
            binding.nameMovieSearchResult.text = film.displayName
            binding.nameMovieOriginalSearchResult.text = film.displayOriginalName
            binding.descriptionYearSearchResult.text = film.displayYear
            binding.descriptionGenreSearchResult.text = film.displayGenre
            binding.descriptionCountrySearchResult.text = film.displayCountry
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FilmForAdapterModel -> TYPE_AWAIT_AND_COMING
            is ThisMonthFilmModel -> TYPE_PREMIER
            else -> Log.e("HomeAdapter", "Unknown type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_AWAIT_AND_COMING -> {
                MoreViewHolderTrendingAndAwait(
                    SearchItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            TYPE_PREMIER -> {
                MoreViewHolderComingThisMonth(
                    SearchItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            else -> {
                throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position)) {
            is FilmForAdapterModel -> (holder as MoreViewHolderTrendingAndAwait).bind(item, navigation)
            is ThisMonthFilmModel -> (holder as MoreViewHolderComingThisMonth).bind(item, navigation)
        }
    }

    class DiffUtilCallBack<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }
    }
}
