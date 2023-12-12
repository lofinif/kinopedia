package com.example.kinopedia.ui.film.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.R
import com.example.kinopedia.databinding.ExternalSourceItemBinding
import com.example.kinopedia.databinding.FilmItemBinding
import com.example.kinopedia.databinding.FilmPageItemBinding
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.utils.UpdateFilmCallBack
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class FilmPageAdapter<T>(private val updateFilmCallBack: UpdateFilmCallBack) :
    ListAdapter<T, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    companion object {
        const val TYPE_ACTORS = 0
        const val TYPE_SIMILAR = 1
        const val TYPE_EXTERNAL = 2

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ExternalSourceModel -> TYPE_EXTERNAL
            is ActorFilmPageModel -> TYPE_ACTORS
            is FilmForAdapterModel -> TYPE_SIMILAR
            else -> Log.e("FilmPageAdapter", "Unknown type")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EXTERNAL -> {
                return ExternalViewHolder(
                    ExternalSourceItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }

            TYPE_SIMILAR -> {
                return SimilarViewHolder(
                    FilmItemBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ), updateFilmCallBack
                )
            }

            TYPE_ACTORS -> {
                return ActorsViewHolder(
                    FilmPageItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }

            else -> {
                throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }
    }

    class ExternalViewHolder(private val binding: ExternalSourceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(externalSource: ExternalSourceModel) {
            Log.e("bind", "bind")
            binding.platform.text = externalSource.displayPlatform
            binding.logo.setOnClickListener {
                openWebPage(externalSource.url)
            }
            if (externalSource.displayPlatform == "Wink" || externalSource.displayPlatform == "Кинотеатр Wink") {
                binding.logo.setImageResource(R.drawable.logo_wink)
            } else {
                GlideToVectorYou
                    .init()
                    .with(binding.root.context)
                    .load(Uri.parse(externalSource.displayLogoUrl), binding.logo)
            }
        }

        private fun openWebPage(url: String) {
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            binding.root.context.startActivity(intent)
        }
    }

    class ActorsViewHolder(private val binding: FilmPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actorFilmPage: ActorFilmPageModel) {
            binding.nameActor.text = actorFilmPage.displayName
            binding.roleActor.text = actorFilmPage.displayDescription
            Picasso.get().load(actorFilmPage.posterUrl).into(binding.avatarActor)
        }
    }

    class SimilarViewHolder(
        private val binding: FilmItemBinding,
        updateFilmCallBack: UpdateFilmCallBack
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmForAdapterModel, updateFilmCallBack: UpdateFilmCallBack) {
            Picasso.get().load(film.posterUrl).into(binding.posterSimilar)
            binding.nameMovie.text = film.displayName
            Log.e("adapter", film.displayName.toString())
            binding.posterSimilar.setOnClickListener {
                updateFilmCallBack.update(film.filmId)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ExternalSourceModel -> (holder as ExternalViewHolder).bind(item)
            is ActorFilmPageModel -> (holder as ActorsViewHolder).bind(item)
            is FilmForAdapterModel -> (holder as SimilarViewHolder).bind(item, updateFilmCallBack)

        }
    }

    class DiffUtilCallBack<T> : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }

    }

}