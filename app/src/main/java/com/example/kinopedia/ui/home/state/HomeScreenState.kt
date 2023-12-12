package com.example.kinopedia.ui.home.state

sealed interface HomeScreenState {
    object Loading : HomeScreenState
    object Error : HomeScreenState
    object Loaded : HomeScreenState
}