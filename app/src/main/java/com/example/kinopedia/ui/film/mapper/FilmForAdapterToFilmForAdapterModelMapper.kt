package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.FilmForAdapterModel
import javax.inject.Inject

class FilmForAdapterToFilmForAdapterModelMapper @Inject constructor(): BaseMapper<FilmForAdapter, FilmForAdapterModel>{

    override fun map(item: FilmForAdapter): FilmForAdapterModel  {
        val dash = "\u2014"
        return  FilmForAdapterModel (item.filmId, item.nameRu ?: item.nameEn ?: dash, item.posterUrl.toString())
    }
}