package com.example.kinopedia.ui.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kinopedia.domain.usecase.GetCountriesGenresUseCase
import com.example.kinopedia.domain.usecase.GetFilmsWithFiltersUseCase
import com.example.kinopedia.factory.PagerSourceFactoryFilters
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.filter.mapper.FilterCountryToFilterCountryModelMapper
import com.example.kinopedia.ui.filter.mapper.FilterGenreToFilterGenreModelMapper
import com.example.kinopedia.ui.filter.model.FilterSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    getCountriesGenresUseCase: GetCountriesGenresUseCase,
    getFilmsWithFiltersUseCase: GetFilmsWithFiltersUseCase,
    mapperCountry: FilterCountryToFilterCountryModelMapper,
    mapperGenre: FilterGenreToFilterGenreModelMapper,
    val factory: PagerSourceFactoryFilters
) : ViewModel() {
    private val _filterSettings = MutableStateFlow(FilterSettings())
    private val filterSettings: StateFlow<FilterSettings> get() = _filterSettings

    val flowGenres = getCountriesGenresUseCase.countriesGenres.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(replayExpirationMillis = 0)
    )
        .map {
            it.genres.map {
                mapperGenre.map(it)
            }
        }.asLiveData()
    val flowCountries = getCountriesGenresUseCase.countriesGenres.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(replayExpirationMillis = 0)
    )
        .map {
            it.countries.map {
                mapperCountry.map(it)
            }
        }.asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val flowFilters by lazy {
        filterSettings.flatMapLatest { settings ->
            getFilmsWithFiltersUseCase.getFilmsWithFilters(providePager(factory, settings))
                .cachedIn(viewModelScope)
        }
    }

    fun updateFilterSettings(filterSettings: FilterSettings) {
        _filterSettings.value = filterSettings
    }

    private fun providePager(factory: PagerSourceFactoryFilters, filterSettings: FilterSettings):
            Pager<Int, KinopoiskFilmModel> {
        return Pager(PagingConfig(20)) {
            factory.create(filterSettings)
        }
    }

}