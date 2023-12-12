package com.example.kinopedia.network.services

import com.example.kinopedia.network.models.Cinemas
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL_OVERPASS = "https://overpass-api.de"

val interceptorOverpass = HttpLoggingInterceptor()
val clientOverpass = OkHttpClient.Builder().addInterceptor(interceptorOverpass).build()


interface ApiServiceOverpass {
    @GET("/api/interpreter")
    suspend fun getCinemas(
        @Query("data") data: String,
    ): Cinemas

    companion object OverpassApi {
        fun create(retrofit: Retrofit): ApiServiceOverpass =
            retrofit.create(ApiServiceOverpass::class.java)
    }
}