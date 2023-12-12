package com.example.kinopedia.ui.filter.mapper

import com.example.kinopedia.network.models.FilterCountry
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.filter.model.FilterCountryModel
import javax.inject.Inject

class FilterCountryToFilterCountryModelMapper @Inject constructor (): BaseMapper<FilterCountry, FilterCountryModel>{
    override fun map(item: FilterCountry): FilterCountryModel {
      return FilterCountryModel(
          item.id,
          item.country
      )
    }
}
