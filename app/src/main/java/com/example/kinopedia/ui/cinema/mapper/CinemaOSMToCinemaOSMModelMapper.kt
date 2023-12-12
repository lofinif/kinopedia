package com.example.kinopedia.ui.cinema.mapper

import com.example.kinopedia.data.cinema.dto.CinemaOSM
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel
import javax.inject.Inject

class CinemaOSMToCinemaOSMModelMapper @Inject constructor() :
    BaseMapper<CinemaOSM, CinemaOSMModel> {


    override fun map(item: CinemaOSM): CinemaOSMModel {
        val latitude = item.lat ?: item.geometry?.get(0)?.lat
        val longitude = item.lon ?: item.geometry?.get(0)?.lon

        return CinemaOSMModel(
            item.id,
            latitude,
            longitude,
            item.tags,
        )
    }
}