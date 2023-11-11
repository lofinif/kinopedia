package com.example.kinopedia.ui.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.ui.home.model.FilmForAdapterModelHome
import com.squareup.picasso.Picasso

class HomeAdapter(private val navigation: NavigationActionListener): ListAdapter<FilmForAdapterModelHome, HomeAdapter.HomeViewHolder>(
    Comparator()
) {
    class HomeViewHolder(private var binding: FilmItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(filmForAdapter: FilmForAdapterModelHome, navigation: NavigationActionListener){
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.poster)
            binding.nameMovie.text = filmForAdapter.displayName
            binding.genreMovie.text = filmForAdapter.displayGenre
            val bundle = Bundle()

            binding.poster.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                bundle.putInt("personId", filmForAdapter.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<FilmForAdapterModelHome>() {
        override fun areItemsTheSame(oldItem: FilmForAdapterModelHome, newItem: FilmForAdapterModelHome): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: FilmForAdapterModelHome, newItem: FilmForAdapterModelHome): Boolean {
            return oldItem.filmId == newItem.filmId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), navigation)
    }

}