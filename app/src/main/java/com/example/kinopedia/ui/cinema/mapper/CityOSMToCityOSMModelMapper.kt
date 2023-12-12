package com.example.kinopedia.ui.cinema.mapper

import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.cinema.model.CityOSMModel
import javax.inject.Inject

class CityOSMToCityOSMModelMapper @Inject constructor(): BaseMapper<CityOSM, CityOSMModel> {
    override fun map(item: CityOSM): CityOSMModel {


        return CityOSMModel(
            item.place_id,
            item.lat,
            item.lon,
            item.display_name,
            item.address
        )
    }
}