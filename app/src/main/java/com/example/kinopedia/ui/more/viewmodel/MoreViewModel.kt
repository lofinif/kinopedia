package com.example.kinopedia.ui.more.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopedia.domain.interactors.GetMoreInteractor
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
  getMoreInteractor: GetMoreInteractor,
) : ViewModel() {

    val awaitFlow = getMoreInteractor.pagedAwaitFlow.cachedIn(viewModelScope)

    val popularFlow = getMoreInteractor.pagedPopularFlow.cachedIn(viewModelScope)

    val premierFlow = getMoreInteractor.pagedPremierFlow.cachedIn(viewModelScope)

}