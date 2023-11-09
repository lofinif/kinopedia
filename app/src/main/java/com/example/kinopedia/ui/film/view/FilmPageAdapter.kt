package com.example.kinopedia.ui.film.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.FilmPageItemBinding
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.mapper.ActorFilmPageToActorFilmPageMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.squareup.picasso.Picasso


class FilmPageAdapter(
    private val mapper: BaseMapper<ActorFilmPage, ActorFilmPageModel>
): ListAdapter<ActorFilmPage, FilmPageAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(private var binding: FilmPageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(actorFilmPage: ActorFilmPageModel) {
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
        val item = getItem(position)
        item?.let {
            holder.bind(mapper.map(it))
        }    }
}