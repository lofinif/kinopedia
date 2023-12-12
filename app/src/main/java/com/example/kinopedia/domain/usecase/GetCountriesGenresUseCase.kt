package com.example.kinopedia.domain.usecase

import com.example.kinopedia.domain.repository.FilterRepository
import javax.inject.Inject

class GetCountriesGenresUseCase @Inject constructor(private val repository: FilterRepository) {
    val countriesGenres get()= repository.countriesGenres
}