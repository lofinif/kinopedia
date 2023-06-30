package com.example.kinopedia.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilmPageItemBinding
import com.example.kinopedia.databinding.FragmentFilmPageBinding
import com.example.kinopedia.network.KinopoiskFilm


class FilmPageAdapter: ListAdapter<KinopoiskFilm, FilmPageAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FragmentFilmPageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kinopoiskFilm: KinopoiskFilm) {
        }
    }

    class Comparator : DiffUtil.ItemCallback<KinopoiskFilm>() {

        override fun areItemsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: KinopoiskFilm, newItem: KinopoiskFilm): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(FragmentFilmPageBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}