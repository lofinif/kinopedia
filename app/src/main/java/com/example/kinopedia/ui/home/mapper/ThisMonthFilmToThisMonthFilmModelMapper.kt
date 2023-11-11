package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.network.models.ThisMonthFilm
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import javax.inject.Inject

class ThisMonthFilmToThisMonthFilmModelMapper @Inject constructor(): BaseMapper<ThisMonthFilm, ThisMonthFilmModel> {
    override fun map(item: ThisMonthFilm): ThisMonthFilmModel {
        val dash = "\u2014"
        val displayGenre = if (item.genres.isNotEmpty()) item.genres[0].genre.toString() else dash
        val displayCountry = if (item.countries.isNotEmpty()) item.countries[0].country.toString() else dash

        return ThisMonthFilmModel(
            item.kinopoiskId,
            item.nameRu ?: item.nameEn ?: dash,
            displayGenre,
            displayCountry,
            item.posterUrl
        )
    }
}