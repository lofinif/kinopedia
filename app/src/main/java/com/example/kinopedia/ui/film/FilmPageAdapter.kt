package com.example.kinopedia.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilmPageItemBinding
import com.example.kinopedia.network.ActorFilmPage
import com.squareup.picasso.Picasso


class FilmPageAdapter: ListAdapter<ActorFilmPage, FilmPageAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FilmPageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(actorFilmPage: ActorFilmPage) {
                binding.nameActor.text = actorFilmPage.displayName
                binding.roleActor.text = actorFilmPage.displayDescription
                Picasso.get().load(actorFilmPage.posterUrl).into(binding.avatarActor)
            }
    }

    class Comparator : DiffUtil.ItemCallback<ActorFilmPage>() {

        override fun areItemsTheSame(oldItem: ActorFilmPage, newItem: ActorFilmPage): Boolean {
            return oldItem.staffId == newItem.staffId
        }

        override fun areContentsTheSame(oldItem: ActorFilmPage, newItem: ActorFilmPage): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(FilmPageItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}