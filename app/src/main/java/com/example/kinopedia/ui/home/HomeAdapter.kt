package com.example.kinopedia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.network.Film
import com.squareup.picasso.Picasso

class HomeAdapter: ListAdapter<Film, HomeAdapter.HomeViewHolder>(Comparator()) {
    class HomeViewHolder(private var binding: FilmItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(film: Film){
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            if(film.genres?.size != 0) binding.genreMovie.text = film.genres?.get(0)?.genre
            val bundle = Bundle()

            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                bundle.putInt("personId", film.filmId)
                 MAIN.navController.navigate(R.id.action_navigation_home_to_filmPageFragment, bundle)
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}