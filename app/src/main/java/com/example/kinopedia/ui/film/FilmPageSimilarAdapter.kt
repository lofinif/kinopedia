package com.example.kinopedia.ui.film


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.network.Film
import com.squareup.picasso.Picasso

class FilmPageSimilarAdapter(private val filmPageFragment: FilmPageFragment): ListAdapter<Film, FilmPageSimilarAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FilmItemBinding, private val filmPageFragment: FilmPageFragment ): RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.poster.setOnClickListener {
                val id = film.filmId
                filmPageFragment.updateFilm(id)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)), filmPageFragment)
    }
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}