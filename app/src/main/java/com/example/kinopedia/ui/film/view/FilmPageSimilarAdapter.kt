package com.example.kinopedia.ui.film.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.utils.UpdateFilmCallBack
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.network.models.Film
import com.squareup.picasso.Picasso

class FilmPageSimilarAdapter(private val updateFilmCallBack: UpdateFilmCallBack):
    ListAdapter<Film, FilmPageSimilarAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FilmItemBinding, private val updateFilmCallBack: UpdateFilmCallBack): RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.poster.setOnClickListener {
                updateFilmCallBack.update(film.filmId)
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
        return FilmViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)), updateFilmCallBack)
    }
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}