package com.example.kinopedia.ui.film

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kinopedia.R

class FilmPageFragment : Fragment() {

    companion object {
        fun newInstance() = FilmPageFragment()
    }

    private lateinit var viewModel: FilmPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilmPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}