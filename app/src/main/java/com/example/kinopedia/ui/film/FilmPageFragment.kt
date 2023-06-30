package com.example.kinopedia.ui.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.kinopedia.MAIN
import com.example.kinopedia.databinding.FragmentFilmPageBinding
import com.example.kinopedia.network.KinopoiskFilm
import com.squareup.picasso.Picasso

class FilmPageFragment : Fragment() {
    private val viewModel: FilmPageViewModel by viewModels()
    private lateinit var binding: FragmentFilmPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFilmPageBinding.inflate(inflater)
        val kinopoiskId = arguments?.getInt("kinopoiskId")
        if(kinopoiskId != null) {
            viewModel.getFilmById(kinopoiskId)
            bind()
        }
        binding.viewModel = viewModel


        return binding.root
    }

    private fun bind(){
        viewModel.data.observe(viewLifecycleOwner) {
            val data = viewModel.getDataKinopoiskFilm()
            Picasso.get().load(data.posterUrl).into(binding.poster)
            binding.yearMovie.text = data.displayYear
            binding.nameMovie.text = data.displayName
            binding.min.text = data.displayFilmLength
            binding.nameMovieOriginal.text = data.nameOriginal
            binding.detailsMovie.text = data.description

        }
    }
}