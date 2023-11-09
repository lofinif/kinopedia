package com.example.kinopedia.ui.film.state

import com.example.kinopedia.ui.film.model.KinopoiskFilmModel

sealed interface FilmScreenState{
    object Loading: FilmScreenState
    object Error: FilmScreenState
    data class Loaded(val filmModel: KinopoiskFilmModel): FilmScreenState
}