package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import javax.inject.Inject

class ExternalSourceToExternalSourceModelMapper @Inject constructor(): BaseMapper<ExternalSource, ExternalSourceModel>{
    override fun map(item: ExternalSource): ExternalSourceModel = ExternalSourceModel(
        item.url,
        item.platform,
        item.logoUrl
    )
}