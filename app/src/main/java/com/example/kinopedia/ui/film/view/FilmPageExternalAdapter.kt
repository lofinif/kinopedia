package com.example.kinopedia.ui.film.view

import android.content.Context
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
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


class FilmPageExternalAdapter(private val mapper: BaseMapper <ExternalSource, ExternalSourceModel>) :
    ListAdapter<ExternalSource, FilmPageExternalAdapter.FilmViewHolder>(Comparator()) {

    class FilmViewHolder(
        private var binding: ExternalSourceItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
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
                    .with(context)
                    .load(Uri.parse(externalSource.displayLogoUrl), binding.logo)
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
        val item = getItem(position)
        item?.let {
            holder.bind(mapper.map(it))
        }
    }
}