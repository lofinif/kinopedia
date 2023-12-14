package com.example.kinopedia.ui.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.databinding.HomeViewPagerItemBinding
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.utils.NavigationActionListener
import com.squareup.picasso.Picasso

class HomeAdapter<T>(private val navigation: NavigationActionListener) :
    ListAdapter<T, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    companion object {
        const val TAG = "HomeAdapter"
        const val TYPE_PREMIER = 0
        const val TYPE_AWAIT_AND_COMING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FilmForAdapterModel -> TYPE_AWAIT_AND_COMING
            is ThisMonthFilmModel -> TYPE_PREMIER
            else -> Log.e(TAG, "Unknown type")
        }
    }

    class HomeViewHolder(private var binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filmForAdapter: FilmForAdapterModel, navigation: NavigationActionListener) {
            val bundle = Bundle()
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.posterSimilar)
            binding.nameMovie.text = filmForAdapter.displayName
            binding.genreMovie.text = filmForAdapter.displayGenre
            binding.posterSimilar.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                bundle.putInt("personId", filmForAdapter.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    class HomeViewHolderViewPager(private val binding: HomeViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ThisMonthFilmModel, navigation: NavigationActionListener) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.genreMovie.text = film.displayGenre
            binding.countryMovie.text = film.displayCountry
            val bundle = Bundle()
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_AWAIT_AND_COMING -> {
                HomeViewHolder(
                    FilmItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            TYPE_PREMIER -> {
                HomeViewHolderViewPager(
                    HomeViewPagerItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            else -> {
                throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FilmForAdapterModel -> (holder as HomeViewHolder).bind(item, navigation)
            is ThisMonthFilmModel -> (holder as HomeViewHolderViewPager).bind(item, navigation)
        }
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
