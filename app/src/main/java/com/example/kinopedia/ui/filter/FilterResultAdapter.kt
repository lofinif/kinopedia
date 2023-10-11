package com.example.kinopedia.ui.filter

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

class FilterResultAdapter: ListAdapter<KinopoiskFilm, FilterResultAdapter.FilterResultViewHolder>(Comparator()) {
    private val items = mutableListOf<KinopoiskFilm>()

    class FilterResultViewHolder(private var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        private val dash = "\u2014"
        fun bind(film: KinopoiskFilm) {
            val genre = if(film.genres.isEmpty())  dash else film.genres[0].genre
            val country = if(film.countries.isEmpty())  dash else film.countries[0].country
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.ratingImdb.text = film.displayRatingImdb
            binding.ratingKinopoisk.text = film.displayRatingKinopoisk
            binding.nameMovieOriginal.text = film.displayOriginalName
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                MAIN.navController.navigate(
                    R.id.action_filterResultFragment_to_filmPageFragment,
                    bundle
                )
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<KinopoiskFilm>() {
        override fun areItemsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterResultViewHolder {
        return FilterResultViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FilterResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addAll(newItems: List<KinopoiskFilm>) {
        items.addAll(newItems)
    }
}