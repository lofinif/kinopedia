package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import javax.inject.Inject

class ThisMonthFilmToThisMonthFilmModelMapper @Inject constructor(): BaseMapper<ThisMonthFilm, ThisMonthFilmModel> {
    override fun map(item: ThisMonthFilm): ThisMonthFilmModel {
        val dash = "\u2014"
        val displayGenre = if (item.genres.isNotEmpty()) item.genres[0].genre.toString() else dash
        val displayCountry = if (item.countries.isNotEmpty()) item.countries[0].country else dash
        val displayOriginalName = if(!item.nameEn.isNullOrEmpty()) item.nameEn else if (!item.nameRu.isNullOrEmpty()) item.nameRu else dash
        val displayName = if(!item.nameRu.isNullOrEmpty()) item.nameRu else if (!item.nameEn.isNullOrEmpty()) item.nameEn else dash

        return ThisMonthFilmModel(
            item.kinopoiskId,
            displayName,
            displayOriginalName,
            displayGenre,
            displayCountry,
            item.year.toString(),
            item.posterUrl,
            dash
        )
    }
}