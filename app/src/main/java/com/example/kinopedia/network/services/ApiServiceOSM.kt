package com.example.kinopedia.network.services

import com.example.kinopedia.data.cinema.dto.CityOSM
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL_OSM = "https://nominatim.openstreetmap.org"

val interceptorOSM = HttpLoggingInterceptor()
val clientOSM = OkHttpClient.Builder().addInterceptor(interceptorOSM).build()

interface ApiServiceOSM {
    @GET("/reverse?format=json")
    suspend fun getCity(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): CityOSM

    companion object OsmApi {
        fun create(retrofit: Retrofit): ApiServiceOSM = retrofit.create(ApiServiceOSM::class.java)
    }
}




