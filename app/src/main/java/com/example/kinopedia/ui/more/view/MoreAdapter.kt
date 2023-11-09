package com.example.kinopedia.ui.more.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.ITEM_TYPE_THIS_MONTH
import com.example.kinopedia.utils.ITEM_TYPE_TOP
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.SearchItemBinding
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.network.models.ThisMonthFilm
import com.squareup.picasso.Picasso

class MoreAdapter(private val navigation: NavigationActionListener) : ListAdapter<FilmForAdapter, RecyclerView.ViewHolder>(
    Comparator()
) {

    private val items = mutableListOf<FilmForAdapter>()
    private val itemsThisMonth = mutableListOf<ThisMonthFilm>()

    class MoreViewHolderTrendingAndAwait(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        private val dash = "\u2014"
        fun bind(filmForAdapter: FilmForAdapter, navigation: NavigationActionListener) {
            val genre = if(filmForAdapter.genres?.isEmpty() == true)  dash else filmForAdapter.genres?.get(0)?.genre
            val country = if(filmForAdapter.countries?.isEmpty() == true)  dash else filmForAdapter.countries?.get(0)?.country
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.poster)
            binding.ratingKinopoisk.text = filmForAdapter.displayRatingKinopoisk
            binding.nameMovie.text = filmForAdapter.displayName
            binding.nameMovieOriginal.text = filmForAdapter.displayOriginalName
            binding.ratingImdb.text = filmForAdapter.dash
            binding.descriptionYear.text = filmForAdapter.displayYear
            binding.descriptionGenre.text = genre
            binding.descriptionCountry.text = country
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                    navigation.navigate(bundle)
            }
        }
    }
    class MoreViewHolderComingThisMonth(private var binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        fun bind(film: ThisMonthFilm, navigation: NavigationActionListener) {
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
            binding.constraintLayout.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                bundle.putString("premier", film.premiereRu)
                    navigation.navigate(bundle)
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

    class Comparator: DiffUtil.ItemCallback<FilmForAdapter>() {
        override fun areItemsTheSame(oldItem: FilmForAdapter, newItem: FilmForAdapter): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: FilmForAdapter, newItem: FilmForAdapter): Boolean {
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
            viewHolder.bind(itemsThisMonth[position], navigation)
        } else if (holder.itemViewType == ITEM_TYPE_TOP && position < items.size) {
            val viewHolder = holder as MoreViewHolderTrendingAndAwait
            viewHolder.bind(items[position], navigation)

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addAllTrendingAndAwait(newItems: List<FilmForAdapter>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
        }
    @SuppressLint("NotifyDataSetChanged")
    fun addAllComingThisMonth(newItems: List<ThisMonthFilm>) {
        itemsThisMonth.clear()
        itemsThisMonth.addAll(newItems)
        notifyDataSetChanged()
    }
}
