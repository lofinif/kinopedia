package com.example.kinopedia.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.ITEM_TYPE_THIS_MONTH
import com.example.kinopedia.ITEM_TYPE_TOP
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.ThisMonthFilm
import com.squareup.picasso.Picasso

class MoreAdapter : ListAdapter<Film, RecyclerView.ViewHolder>(Comparator()) {

    private val items = mutableListOf<Film>()
    private val itemsThisMonth = mutableListOf<ThisMonthFilm>()

    class MoreViewHolderTrendingAndAwait(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        private val dash = "\u2014"
        fun bind(film: Film) {
            val genre = if(film.genres?.isEmpty() == true)  dash else film.genres?.get(0)?.genre
            val country = if(film.countries?.isEmpty() == true)  dash else film.countries?.get(0)?.country
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.ratingKinopoisk.text = film.displayRatingKinopoisk
            binding.nameMovie.text = film.displayName
            binding.nameMovieOriginal.text = film.displayOriginalName
            binding.ratingImdb.text = film.dash
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                    MAIN.navController.navigate(
                        R.id.action_moreFragment_to_filmPageFragment,
                        bundle)
            }
        }
    }
    class MoreViewHolderComingThisMonth(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(film: ThisMonthFilm) {
            val genre = if(film.genres?.isEmpty() == true)  film.dash else film.genres?.get(0)?.genre
            val country = if(film.countries?.isEmpty() == true)  film.dash else film.countries?.get(0)?.country
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.ratingKinopoisk.text = film.displayRating
            binding.ratingImdb.text = film.displayRating
            binding.nameMovie.text = film.displayName
            binding.nameMovieOriginal.text = film.displayOriginalName
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                bundle.putString("premier", film.premiereRu)
                    MAIN.navController.navigate(
                        R.id.action_moreFragment_to_filmPageFragment,
                        bundle)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position < itemsThisMonth.size){
            ITEM_TYPE_THIS_MONTH
        } else {
            ITEM_TYPE_TOP
        }
    }
    override fun getItemCount(): Int {
        return if (items.isNotEmpty()){
            items.size
        } else {
            itemsThisMonth.size
        }
    }

    class Comparator: DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_THIS_MONTH) {
            MoreViewHolderComingThisMonth(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            MoreViewHolderTrendingAndAwait(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_TYPE_THIS_MONTH && position < itemsThisMonth.size) {
            val viewHolder = holder as MoreViewHolderComingThisMonth
            viewHolder.bind(itemsThisMonth[position])
        } else if (holder.itemViewType == ITEM_TYPE_TOP && position < items.size) {
            val viewHolder = holder as MoreViewHolderTrendingAndAwait
            viewHolder.bind(items[position])

        }
    }
    fun addAllTrendingAndAwait(newItems: List<Film>) {
        items.clear()
        items.addAll(newItems)
        }
    fun addAllComingThisMonth(newItems: List<ThisMonthFilm>) {
        itemsThisMonth.clear()
        itemsThisMonth.addAll(newItems)
    }
}
