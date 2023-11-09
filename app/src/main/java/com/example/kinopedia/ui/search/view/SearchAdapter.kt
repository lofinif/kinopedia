package com.example.kinopedia.ui.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.databinding.FilterTopItemBinding
import com.example.kinopedia.network.models.Film
import com.squareup.picasso.Picasso

class SearchAdapter(private val navigation: NavigationActionListener): ListAdapter<Film, SearchAdapter.SearchViewHolder>(
    Comparator()
) {

    class SearchViewHolder(private var binding: FilterTopItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val bundle = Bundle()
        private val dash = "\u2014"
        fun bind(film: Film, navigation: NavigationActionListener) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            if(film.genres?.size == 0) binding.genreMovie.text = dash else binding.genreMovie.text = film.genres?.get(0)?.genre
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                navigation.navigate(bundle)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(FilterTopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), navigation)
    }

}