package com.example.kinopedia.ui.film.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.R
import com.example.kinopedia.databinding.ExternalSourceItemBinding
import com.example.kinopedia.network.models.ExternalSource
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


class FilmPageExternalAdapter() :
    ListAdapter<ExternalSource, FilmPageExternalAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(
        private var binding: ExternalSourceItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(externalSource: ExternalSource) {
            binding.platform.text = externalSource.platform
            binding.logo.setOnClickListener {
                openWebPage(externalSource.url)
            }
            if (externalSource.platform == "Wink" || externalSource.platform == "Кинотеатр Wink") {
                binding.logo.setImageResource(R.drawable.logo_wink)
            } else {
                GlideToVectorYou
                    .init()
                    .with(context)
                    .load(Uri.parse(externalSource.logoUrl), binding.logo)
            }
        }

        private fun openWebPage(url: String) {
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            context.startActivity(intent)
        }
    }

    class Comparator : DiffUtil.ItemCallback<ExternalSource>() {

        override fun areItemsTheSame(oldItem: ExternalSource, newItem: ExternalSource): Boolean {
            return oldItem.platform == newItem.platform
        }

        override fun areContentsTheSame(oldItem: ExternalSource, newItem: ExternalSource): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            ExternalSourceItemBinding.inflate(LayoutInflater.from(parent.context)),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}