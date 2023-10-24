package com.example.kinopedia.ui.cinema.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.CinemaItemBinding
import com.example.kinopedia.network.models.CinemaOSM


class CinemaAdapter(): ListAdapter<CinemaOSM, CinemaAdapter.CinemaViewHolder>(Comparator()) {

    class CinemaViewHolder(private var binding: CinemaItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cinema: CinemaOSM) {
                binding.cinemaName.text = cinema.tags?.name
                binding.descriptionName.text = cinema.tags?.address
        }
    }

    class Comparator: DiffUtil.ItemCallback<CinemaOSM>() {
        override fun areItemsTheSame(oldItem: CinemaOSM, newItem: CinemaOSM): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CinemaOSM, newItem: CinemaOSM): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        return CinemaViewHolder(CinemaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}