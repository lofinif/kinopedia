package com.example.kinopedia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_OSM = "https://nominatim.openstreetmap.org"
private const val BASE_URL_OVERPASS = "https://overpass-api.de"

private val moshiOSM = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

val interceptorOSM = HttpLoggingInterceptor()
val clientOSM = OkHttpClient.Builder().addInterceptor(interceptorOSM).build()
private val retrofitOSM = Retrofit.Builder().client(clientOSM)
    .addConverterFactory(MoshiConverterFactory.create(moshiOSM))
    .baseUrl(BASE_URL_OSM).build()

private val retrofitOverpass = Retrofit.Builder().client(clientOSM)
    .addConverterFactory(MoshiConverterFactory.create(moshiOSM))
    .baseUrl(BASE_URL_OVERPASS).build()

interface ApiServiceOSM {
    @GET("/reverse?format=json")
    suspend fun getCity(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ) : CityOSM
}

object OSMApi{
    val retrofitService: ApiServiceOSM by lazy { retrofitOSM.create(ApiServiceOSM::class.java) }
}




