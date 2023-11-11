package com.example.kinopedia.ui.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.databinding.HomeViewPagerItemBinding
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.utils.NavigationActionListener
import com.squareup.picasso.Picasso

class HomeViewPagerAdapter(private val navigation: NavigationActionListener) : RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewHolder>() {
    private val films = mutableListOf<ThisMonthFilmModel>()

     class HomeViewHolder(private val binding: HomeViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ThisMonthFilmModel, navigation: NavigationActionListener) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            binding.genreMovie.text = film.displayGenre
            binding.countryMovie.text = film.displayCountry
            val bundle = Bundle()
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.filmId)
                navigation.navigate(bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(films[position], navigation)
    }

    override fun getItemCount(): Int {
        return films.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addAllComingThisMonth(newItems: List<ThisMonthFilmModel>) {
        films.clear()
        films.addAll(newItems)
        notifyDataSetChanged()
    }
}
