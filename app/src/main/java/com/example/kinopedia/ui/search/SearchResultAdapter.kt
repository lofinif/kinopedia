package com.example.kinopedia.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.network.KinopoiskFilm
import com.squareup.picasso.Picasso


class SearchResultAdapter: ListAdapter<KinopoiskFilm, SearchResultAdapter.SearchViewHolder>(Comparator()) {
    private val items = mutableListOf<KinopoiskFilm>()


    class SearchViewHolder(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(film: KinopoiskFilm) {
            val genre = if(film.genres.isEmpty() == true)  "-" else film.genres.get(0).genre
            val country = if(film.countries.isEmpty() == true)  "-" else film.countries.get(0).country
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.nameMovieOriginal.text = film.displayOriginalName
            binding.ratingKinopoisk.text = film.displayRatingKinopoisk
            binding.ratingImdb.text = film.displayRatingImdb
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                MAIN.navController.navigate(R.id.action_searchResultFragment_to_filmPageFragment, bundle)
            }
        }
    }

        class Comparator: DiffUtil.ItemCallback<KinopoiskFilm>() {
        override fun areItemsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }
        override fun areContentsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    fun addAll(newItems: List<KinopoiskFilm>) {
        items.addAll(newItems)
    }

}