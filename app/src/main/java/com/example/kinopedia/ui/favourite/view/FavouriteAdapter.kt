package com.example.kinopedia.ui.favourite.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FavouriteFilmsItemBinding
import com.example.kinopedia.ui.favourite.model.FavouriteEntityModel
import com.example.kinopedia.utils.FavouriteAdapterCallback
import com.example.kinopedia.utils.NavigationActionListener
import com.squareup.picasso.Picasso

class FavouriteAdapter(
    private val navigation: NavigationActionListener,
    private val callback: FavouriteAdapterCallback
) : ListAdapter<FavouriteEntityModel,
        FavouriteAdapter.FavouriteViewHolder>(Comparator()) {
    class FavouriteViewHolder(
        private var binding: FavouriteFilmsItemBinding,
        private val callback: FavouriteAdapterCallback,
        private val navigation: NavigationActionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        val bundle = Bundle()

        @SuppressLint("ClickableViewAccessibility")
        fun bind(film: FavouriteEntityModel) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.descriptionYear.text = film.displayYear
            binding.descriptionGenre.text = film.displayGenre
            binding.descriptionCountry.text = film.displayCountry
            binding.nameMovieOriginal.text = film.displayNameOriginal
            binding.ratingKinopoisk.text = film.displayKinopoiskRating
            binding.ratingImdb.text = film.displayImdbRating
            bundle.putInt("filmId", film.filmId)

            binding.constraintLayout.setOnClickListener {
                navigation.navigate(bundle)
            }
            binding.constraintLayout.setOnTouchListener(
                (View.OnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        callback.updateFilmId(film.filmId)
                        callback.updateAdapterPosition(adapterPosition)
                    }
                    false
                })
            )
        }
    }

    class Comparator : DiffUtil.ItemCallback<FavouriteEntityModel>() {
        override fun areItemsTheSame(
            oldItem: FavouriteEntityModel,
            newItem: FavouriteEntityModel
        ): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(
            oldItem: FavouriteEntityModel,
            newItem: FavouriteEntityModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            FavouriteFilmsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), callback, navigation
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}