package com.example.kinopedia.domain.interactors

import com.example.kinopedia.domain.repository.MoreRepository
import javax.inject.Inject

class GetMoreInteractor@Inject constructor(private val repository: MoreRepository) {
    val pagedPopularFlow
        get() = repository.pagedPopularFlow
    val pagedAwaitFlow
        get() = repository.pagedAwaitFlow
    val pagedPremierFlow
        get() = repository.pagedPremierFlow

}