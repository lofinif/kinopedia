package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import javax.inject.Inject

class ActorFilmPageToActorFilmPageMapper @Inject constructor(): BaseMapper<ActorFilmPage, ActorFilmPageModel> {

    override fun map(item: ActorFilmPage): ActorFilmPageModel {
         val dash = "\u2014"

        val displayName = if (!item.nameRu.isNullOrBlank()) item.nameRu
        else item.nameEn

        val displayDescription = if (!item.description.isNullOrBlank()) item.description
        else  item.professionText?.replace("ы$".toRegex(),"")?.lowercase() ?: dash

        return ActorFilmPageModel(
            displayName,
            displayDescription,
            item.posterUrl
            )
    }

}