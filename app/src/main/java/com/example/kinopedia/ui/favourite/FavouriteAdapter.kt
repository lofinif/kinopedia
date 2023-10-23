package com.example.kinopedia.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.FavouriteAdapterCallback
import com.example.kinopedia.NavigationActionListener
import com.example.kinopedia.data.FavouriteEntity
import com.example.kinopedia.databinding.FavouriteFilmsItemBinding
import com.squareup.picasso.Picasso

class FavouriteAdapter(private val navigation: NavigationActionListener,
                       private val callback: FavouriteAdapterCallback): ListAdapter <FavouriteEntity,
        FavouriteAdapter.FavouriteViewHolder>(Comparator()) {
    class FavouriteViewHolder(private var binding: FavouriteFilmsItemBinding,
                              private val callback: FavouriteAdapterCallback,
                              private val navigation: NavigationActionListener): RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle()
        @SuppressLint("ClickableViewAccessibility")
        fun bind(film: FavouriteEntity) {
                Picasso.get().load(film.posterUrl).into(binding.poster)
                binding.nameMovie.text = film.nameRu
                binding.descriptionYear.text = film.year
                binding.descriptionGenre.text = film.genre
                binding.descriptionCountry.text = film.country
                binding.nameMovieOriginal.text = film.nameOriginal
                binding.ratingKinopoisk.text = film.ratingKinopoisk
                binding.ratingImdb.text = film.ratingImdb
                bundle.putInt("filmId", film.filmId)

            binding.constraintLayout.setOnClickListener {
                navigation.navigate(bundle)
            }
            binding.constraintLayout.setOnTouchListener(
            (View.OnTouchListener { _, event ->
                if (event.action==MotionEvent.ACTION_DOWN) {
                    callback.updateFilmId(film.filmId)
                    callback.updateAdapterPosition(adapterPosition)
                }
                false
            })
            )
        }
    }

    class Comparator: DiffUtil.ItemCallback<FavouriteEntity>() {
        override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(FavouriteFilmsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback, navigation)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}