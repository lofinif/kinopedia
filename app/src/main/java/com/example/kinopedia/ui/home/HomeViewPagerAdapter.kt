package com.example.kinopedia.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.MAIN
import com.example.kinopedia.R
import com.example.kinopedia.databinding.HomeViewPagerItemBinding
import com.example.kinopedia.network.ThisMonthFilm
import com.squareup.picasso.Picasso

class HomeViewPagerAdapter : RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewHolder>() {
    private val films = mutableListOf<ThisMonthFilm>()

     class HomeViewHolder(private val binding: HomeViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ThisMonthFilm) {
            Picasso.get().load(film.posterUrl).into(binding.poster)
            binding.nameMovie.text = film.displayName
            if (film.genres?.isNotEmpty() == true) binding.genreMovie.text = film.genres[0].genre
            if (film.countries?.isNotEmpty() == true) binding.countryMovie.text = film.countries[0].country
            val bundle = Bundle()
            binding.poster.setOnClickListener {
                bundle.putInt("filmId", film.kinopoiskId)
                bundle.putString("premier", film.premiereRu)
                MAIN.navController.navigate(R.id.action_navigation_home_to_filmPageFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int {
        return films.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addAllComingThisMonth(newItems: List<ThisMonthFilm>) {
        films.clear()
        films.addAll(newItems)
        notifyDataSetChanged()
    }
}
