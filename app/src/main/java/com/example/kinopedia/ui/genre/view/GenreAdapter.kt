package com.example.kinopedia.ui.genre.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.squareup.picasso.Picasso

class GenreAdapter(private val navigation: NavigationActionListener): ListAdapter<KinopoiskFilm, GenreAdapter.GenreViewHolder>(
    Comparator()
) {
    private val items = mutableListOf<KinopoiskFilm>()


    class GenreViewHolder(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        private val dash = "\u2014"
        fun bind(film: KinopoiskFilm, navigation: NavigationActionListener) {
            val genre = if(film.genres.isEmpty())  dash else film.genres[0].genre
            val country = if(film.countries.isEmpty())  dash else film.countries[0].country
            binding.nameMovie.text = film.displayName
            binding.nameMovieOriginal.text = film.displayOriginalName
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.ratingKinopoisk.text = film.displayRatingKinopoisk
            binding.ratingImdb.text = film.displayRatingImdb
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                navigation.navigate(bundle)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position), navigation)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addAll(newItems: List<KinopoiskFilm>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}