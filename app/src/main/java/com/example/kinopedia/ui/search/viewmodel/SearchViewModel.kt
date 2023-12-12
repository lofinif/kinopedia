package com.example.kinopedia.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kinopedia.factory.PagerSourceFactoryKeyWord
import com.example.kinopedia.domain.usecase.GetTopFilmsSearchPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getTopFilmsSearchPageUseCase: GetTopFilmsSearchPageUseCase,
    val factory: PagerSourceFactoryKeyWord
) : ViewModel() {

    val flowTop = getTopFilmsSearchPageUseCase.pagedFlow.cachedIn(viewModelScope)

    private var keyWordFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val flowKeyWord = keyWordFlow.flatMapLatest { newKeyWord ->
        Pager(PagingConfig(20)) {
            factory.create(newKeyWord)
        }.flow
    }.cachedIn(viewModelScope)

    fun updateKeyWord(newKeyWord: String){
        keyWordFlow.value = newKeyWord
    }
}