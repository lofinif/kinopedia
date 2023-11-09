package com.example.kinopedia.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.squareup.picasso.Picasso

class HomeAdapter(private val navigation: NavigationActionListener): ListAdapter<FilmForAdapter, HomeAdapter.HomeViewHolder>(
    Comparator()
) {
    class HomeViewHolder(private var binding: FilmItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(filmForAdapter: FilmForAdapter, navigation: NavigationActionListener){
            Picasso.get().load(filmForAdapter.posterUrl).into(binding.poster)
            binding.nameMovie.text = filmForAdapter.displayName
            if(filmForAdapter.genres?.size != 0) binding.genreMovie.text = filmForAdapter.genres?.get(0)?.genre
            val bundle = Bundle()

            binding.poster.setOnClickListener {
                bundle.putInt("filmId", filmForAdapter.filmId)
                bundle.putInt("personId", filmForAdapter.filmId)
                navigation.navigate(bundle)
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position), navigation)
    }

}