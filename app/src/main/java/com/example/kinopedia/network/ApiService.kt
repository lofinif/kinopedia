package com.example.kinopedia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

enum class LoadingStatus { LOADING, ERROR, DONE }


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
    suspend fun getAwaitFilms(
        @Query("page") page: Int
    ): TopFilms

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularFilms(@Query("page") page: Int): TopFilms
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun getTopFilms(): TopFilms

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id : Int): KinopoiskFilm
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") id : Int): KinopoiskSimilarFilms

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/premieres")
    suspend fun getFilmsThisMonth(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int): ThisMonthFilms
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v1/staff")
    suspend fun getActorsAndStaff(
        @Query("filmId") id: Int): List<ActorFilmPage>
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): Filters
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
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

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v2.2/films/{id}/external_sources")
    suspend fun getExternalSources(
        @Path("id") id: Int): ExternalSources
    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v1/staff/{id}")
    suspend fun getPerson(
        @Path("id") id: Int): Person

    @Headers("X-API-KEY: 6246d18e-5a4f-48aa-821f-805472008972")
    @GET("/api/v1/persons")
    suspend fun getPersons(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ) : Persons
}

object FilmApi{
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
