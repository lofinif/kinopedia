package com.example.kinopedia.domain.usecase

import com.example.kinopedia.domain.repository.SearchRepository
import javax.inject.Inject

class GetTopFilmsSearchPageUseCase @Inject constructor(private val repository: SearchRepository) {
    val pagedFlow get() = repository.pagedFlow
}
