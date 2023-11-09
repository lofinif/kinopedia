package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import javax.inject.Inject

class KinopoiskFilmToKinopoiskFilmModelMapper @Inject constructor(): BaseMapper<KinopoiskFilm, KinopoiskFilmModel>{
    override fun map(item: KinopoiskFilm): KinopoiskFilmModel {
        val dash = "\u2014"

        val displayRatingKinopoisk: String = item.ratingKinopoisk?.toString()
            ?: if (item.ratingAwait != null) "${item.ratingAwait}%" else dash

        val displayGenre = if (item.genres.isNotEmpty()) item.genres[0].genre.toString() else dash
        val displayCountry = if (item.countries.isNotEmpty()) item.countries[0].country else dash
        val displayYear = item.year?.toString() ?: dash
        val displayLength = item.filmLength?.toString() ?: dash
        val displayDetails = "$displayYear, $displayGenre, $displayLength, $displayCountry"

        return KinopoiskFilmModel(
            item.nameRu ?: item.nameOriginal ?: item.nameEn ?: dash,
            item.nameOriginal ?: item.nameEn ?: item.nameRu ?: dash,
            displayDetails,
            item.filmLength?.toString() ?: dash,
            item.description ?: dash,
            item.posterUrl,
            displayGenre,
            displayCountry,
            displayRatingKinopoisk,
            item.ratingImdb?.toString() ?: dash
        )
    }
}
