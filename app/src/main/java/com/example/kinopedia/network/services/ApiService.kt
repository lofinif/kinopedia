package com.example.kinopedia.network.services

import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.network.models.FilmsByGenre
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.kinopedia.network.models.ThisMonthFilms
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val client = OkHttpClient.Builder().addInterceptor(Interceptor()).build()


class Interceptor : Interceptor {
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
    suspend fun getTopFilms(
        @Query("page") page: Int
    ): TopFilms

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: Int
    ): KinopoiskFilm

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id: Int
    ): KinopoiskSimilarFilms

    @GET("/api/v2.2/films/premieres")
    suspend fun getFilmsThisMonth(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int
    ): ThisMonthFilms

    @GET("/api/v1/staff")
    suspend fun getActorsAndStaff(
        @Query("filmId") id: Int
    ): List<ActorFilmPage>

    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): Filters

    @GET("/api/v2.2/films")
    suspend fun getFilmByFilters(
        @Query("countries") countries: Array<Int>? = null,
        @Query("genres") genres: Array<Int>? = null,
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "FILM",
        @Query("keyword") keyword: String? = null,
        @Query("ratingFrom") ratingFrom: Int = 7,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 2020,
        @Query("yearTo") yearTo: Int = 2023,
        @Query("page") page: Int
    ): FilmsByGenre

    @GET("/api/v2.2/films/{id}/external_sources")
    suspend fun getExternalSources(
        @Path("id") id: Int
    ): ExternalSources

    companion object FilmApi {
        fun create(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    }
}


