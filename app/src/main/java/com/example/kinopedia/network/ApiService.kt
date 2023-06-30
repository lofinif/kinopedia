package com.example.kinopedia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

val interceptor = HttpLoggingInterceptor()
val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder().client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()


interface ApiService {
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/top?type=TOP_AWAIT_FILMS")
    suspend fun getAwaitFilms(): TopFilms

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularFilms(): TopFilms

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id : Int
    ): KinopoiskFilm

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/premieres")
    suspend fun getFilmsThisMonth(
        @Query("year") year: Int,
        @Query("month") month: String): ThisMonthFilms
}

object FilmApi{
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
