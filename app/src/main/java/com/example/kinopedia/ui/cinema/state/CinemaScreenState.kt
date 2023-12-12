package com.example.kinopedia.ui.cinema.state

interface CinemaScreenState {
    object Loading : CinemaScreenState
    object Error : CinemaScreenState
    object Loaded : CinemaScreenState
}