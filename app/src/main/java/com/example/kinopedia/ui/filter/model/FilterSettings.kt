package com.example.kinopedia.ui.filter.model

import android.icu.util.Calendar

data class FilterSettings (
    val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    var countryId: Int = -1,
    var genreId: Int = -1,
    var sortType: String = "RATING",
    var type: String = "ALL",
    var keyWord: String? = null,
    var minRating: Int = 0,
    var maxRating: Int = 10,
    var selectedYearFrom: Int = currentYear - 200,
    var selectedYearTo: Int = currentYear,
    var selectedCountry: String = "",
    var selectedGenre: String = "",
    var selectedYears: String = "",
    var selectedSort: String = "",
    var page: Int = 1
){
    fun clearFilters() {
        countryId = -1
        genreId = -1
        sortType = "RATING"
        type = "ALL"
        keyWord = null
        minRating = 0
        maxRating = 10
        selectedYearFrom = currentYear - 200
        selectedYearTo = currentYear
        selectedCountry = ""
        selectedGenre = ""
        selectedYears = ""
        selectedSort = ""
        page = 1
    }
}
