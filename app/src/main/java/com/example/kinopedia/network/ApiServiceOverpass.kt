package com.example.kinopedia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_OVERPASS = "https://overpass-api.de"

private val moshiOverpass = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

val interceptorOverpass = HttpLoggingInterceptor()
val clientOverpass = OkHttpClient.Builder().addInterceptor(interceptorOverpass).build()

private val retrofitOverpass = Retrofit.Builder().client(clientOverpass)
    .addConverterFactory(MoshiConverterFactory.create(moshiOverpass))
    .baseUrl(BASE_URL_OVERPASS).build()

interface ApiServiceOverpass {
    @GET("/api/interpreter")
    suspend fun getCinemas(
        @Query("data") data: String,
    ): Cinemas
}
object OverpassApi{
    val retrofitService: ApiServiceOverpass by lazy { retrofitOverpass.create(ApiServiceOverpass::class.java) }
}