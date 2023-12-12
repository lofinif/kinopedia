package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import javax.inject.Inject

class FilmForAdapterToFilmForAdapterModelMapper @Inject constructor() : BaseMapper<FilmForAdapter, FilmForAdapterModel>   {
    override fun map(item: FilmForAdapter): FilmForAdapterModel {
        val dash = "\u2014"
        val displayGenre = if (item.genres?.isNotEmpty() == true) item.genres[0].genre.toString() else dash
        val displayCountry = if (item.countries?.isNotEmpty() == true) item.countries[0].country else dash

        return FilmForAdapterModel(
            item.filmId,
            item.nameRu?: item.nameEn ?: dash,
            item.nameEn?: item.nameRu ?: dash,
            displayGenre,
            displayCountry,
            item.rating?: dash,
            item.year ?: dash,
            dash,
            item.posterUrl
        )
    }

}
