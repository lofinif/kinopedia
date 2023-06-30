package com.example.kinopedia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.HomeItemBinding
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.ThisMonthFilm
import com.squareup.picasso.Picasso

class HomeAdapterThisMonth: ListAdapter<ThisMonthFilm, HomeAdapterThisMonth.HomeViewHolder>(Comparator()) {

    class HomeViewHolder(private var binding: HomeItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(film: ThisMonthFilm){
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.genreMovie.text = film.genres[0].genre
            val bundle = Bundle()

            binding.poster.setOnClickListener {
                bundle.putInt("kinopoiskId", film.kinopoiskId)
                MAIN.navController.navigate(R.id.action_navigation_home_to_filmPageFragment, bundle)
            }
        }
    }

    class Comparator: DiffUtil.ItemCallback<ThisMonthFilm>() {
        override fun areItemsTheSame(oldItem: ThisMonthFilm, newItem: ThisMonthFilm): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }
        override fun areContentsTheSame(oldItem: ThisMonthFilm, newItem: ThisMonthFilm): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}