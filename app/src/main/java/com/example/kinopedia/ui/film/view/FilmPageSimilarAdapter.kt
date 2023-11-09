package com.example.kinopedia.ui.film.view


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.utils.UpdateFilmCallBack
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.FilmForAdapterModel
import com.squareup.picasso.Picasso

class FilmPageSimilarAdapter(private val updateFilmCallBack: UpdateFilmCallBack,
    private val mapper: BaseMapper <FilmForAdapter, FilmForAdapterModel>):
    ListAdapter<FilmForAdapter, FilmPageSimilarAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FilmItemBinding, private val updateFilmCallBack: UpdateFilmCallBack): RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmForAdapterModel) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            Log.e("adapter", film.displayName.toString())
            binding.poster.setOnClickListener {
                updateFilmCallBack.update(film.filmId)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<FilmForAdapter>() {
        override fun areItemsTheSame(oldItem: FilmForAdapter, newItem: FilmForAdapter): Boolean {
            return oldItem.filmId == newItem.filmId
        }
        override fun areContentsTheSame(oldItem: FilmForAdapter, newItem: FilmForAdapter): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(FilmItemBinding.inflate(LayoutInflater.from(parent.context)), updateFilmCallBack)
    }
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(mapper.map(it))
        }
    }
}