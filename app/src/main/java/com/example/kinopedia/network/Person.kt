package com.example.kinopedia.network

import android.util.Log

data class Person(
    val personId: Int?,
    val webUrl: String?,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: String?,
    val birthday: String?,
    val death: String?,
    val age: Int?,
    val birthplace: String?,
    val deathplace: String?,
    val hasAwards: Int?,
    val profession: String?,
    val facts: List<String>?,
    val spouses: List<Spouses>?,
    val films: List<FilmsActorPage>?
){
    private val dash = "\u2014"
    val displayNameRu = if (nameRu.isNullOrEmpty()) nameEn else nameRu
    val displayNameEn = if (nameEn.isNullOrEmpty()) nameRu else nameEn
    private val lastDigitAge = age!! % 10
    val displayAge = if (age!! == 0) ""
            else if (age == 1) "$age год"
            else if (age <= 4) "$age года"
            else if (age <= 20) "$age лет"
            else if (lastDigitAge == 0) "$age лет"
            else if (lastDigitAge == 1) "$age год"
            else if (lastDigitAge < 5) "$age года"
                else "$age лет"
    val displayBirthday = if (birthday.isNullOrEmpty()) dash else birthday
    val displayBirthplace = if (birthplace.isNullOrEmpty()) dash else birthplace
    val displayProfession = if (profession.isNullOrEmpty()) dash else profession

}
