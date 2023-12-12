package com.example.kinopedia.ui.cinema.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.CinemaItemBinding
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel


class CinemaAdapter : ListAdapter<CinemaOSMModel, CinemaAdapter.CinemaViewHolder>(Comparator()) {

    class CinemaViewHolder(private var binding: CinemaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cinema: CinemaOSMModel) {
            binding.cinemaName.text = cinema.tags?.name
            binding.descriptionName.text = cinema.tags?.address
        }
    }

    class Comparator : DiffUtil.ItemCallback<CinemaOSMModel>() {
        override fun areItemsTheSame(oldItem: CinemaOSMModel, newItem: CinemaOSMModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CinemaOSMModel, newItem: CinemaOSMModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        return CinemaViewHolder(
            CinemaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}