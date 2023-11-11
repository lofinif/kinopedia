package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModelHome
import javax.inject.Inject

class FilmForAdapterToFilmForAdapterModelMapperHome @Inject constructor() : BaseMapper<FilmForAdapter, FilmForAdapterModelHome>   {
    override fun map(item: FilmForAdapter): FilmForAdapterModelHome {
        val dash = "\u2014"
        val displayGenre = if (item.genres!!.isNotEmpty()) item.genres[0].genre.toString() else dash

       return FilmForAdapterModelHome(
            item.filmId,
            item.nameRu?: item.nameEn ?: dash,
            displayGenre,
            item.posterUrl
        )
    }

}
