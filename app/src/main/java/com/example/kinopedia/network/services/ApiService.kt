package com.example.kinopedia.network.services

import com.example.kinopedia.utils.BASE_URL
import com.example.kinopedia.network.models.ActorFilmPage
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.network.models.FilmsByGenre
import com.example.kinopedia.network.models.Filters
import com.example.kinopedia.network.models.KinopoiskFilm
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.kinopedia.network.models.ThisMonthFilms
import com.example.kinopedia.network.models.TopFilms
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

enum class LoadingStatus { LOADING, ERROR, DONE, DEFAULT }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

val client = OkHttpClient.Builder().addInterceptor(Interceptor()).build()

private val retrofit = Retrofit.Builder().client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithHeaders = request.newBuilder().header(
            "X-API-Key",
            "6246d18e-5a4f-48aa-821f-805472008972"
        ).build()

        return chain.proceed(requestWithHeaders)
    }

}

interface ApiService {
    @GET("/api/v2.2/films/top?type=TOP_AWAIT_FILMS")
    suspend fun getAwaitFilms(
        @Query("page") page: Int
    ): TopFilms

    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularFilms(@Query("page") page: Int): TopFilms

    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun getTopFilms(): TopFilms

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id : Int): KinopoiskFilm

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id : Int): KinopoiskSimilarFilms

    @GET("/api/v2.2/films/premieres")
    suspend fun getFilmsThisMonth(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int): ThisMonthFilms
    @GET("/api/v1/staff")
    suspend fun getActorsAndStaff(
        @Query("filmId") id: Int): List<ActorFilmPage>
    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): Filters
    @GET("/api/v2.2/films")
    suspend fun getFilmByFilters(
        @Query("countries") countries: Array<Int>? = null,
        @Query("genres") genres: Array<Int>? = null,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("keyword") keyword: String? = null,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): FilmsByGenre

    @GET("/api/v2.2/films/{id}/external_sources")
    suspend fun getExternalSources(
        @Path("id") id: Int): ExternalSources

}

object FilmApi{
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
