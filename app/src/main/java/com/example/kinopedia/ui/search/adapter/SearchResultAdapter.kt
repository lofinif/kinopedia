package com.example.kinopedia.ui.search.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.squareup.picasso.Picasso


class SearchResultAdapter(private val navigation: NavigationActionListener): PagingDataAdapter<KinopoiskFilmModel, SearchResultAdapter.SearchViewHolder>(
    Comparator()
) {
    class SearchViewHolder(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(film: KinopoiskFilmModel, navigation: NavigationActionListener) {
            Picasso.get().load(film.posterUrl).into(binding.posterSearchResult)
            binding.nameMovieSearchResult.text = film.displayName
            binding.descriptionYearSearchResult.text = film.displayYear
            binding.descriptionGenreSearchResult.text = film.displayGenre
            binding.descriptionCountrySearchResult.text = film.displayCountries
            binding.nameMovieOriginalSearchResult.text = film.displayNameOriginal
            binding.ratingKinopoiskSearchResult.text = film.displayRatingKinopoisk
            binding.ratingImdbSearchResult.text = film.displayRatingImdb
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                navigation.navigate(bundle)
            }
        }
    }

        class Comparator: DiffUtil.ItemCallback<KinopoiskFilmModel>() {
        override fun areItemsTheSame(oldItem: KinopoiskFilmModel, newItem: KinopoiskFilmModel): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: KinopoiskFilmModel, newItem: KinopoiskFilmModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, navigation) }
    }
}