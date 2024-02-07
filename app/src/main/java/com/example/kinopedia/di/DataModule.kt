package com.example.kinopedia.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.kinopedia.data.cinema.CinemaRepositoryImpl
import com.example.kinopedia.data.cinema.dto.CinemaOSM
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.database.FavouriteDatabase
import com.example.kinopedia.data.favourite.FavouriteRepositoryImpl
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.FilmRepositoryImpl
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.filter.FilterRepositoryImpl
import com.example.kinopedia.data.home.HomeRepositoryImpl
import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.data.more.MorePagingSource
import com.example.kinopedia.data.more.MorePagingSourceAwait
import com.example.kinopedia.data.more.MorePagingSourcePremier
import com.example.kinopedia.data.more.MoreRepositoryImpl
import com.example.kinopedia.data.search.PagingSourceSearchTopFilms
import com.example.kinopedia.data.search.SearchRepositoryImpl
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.kinopedia.domain.repository.FavouriteRepository
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.network.models.FilterCountry
import com.example.kinopedia.network.models.FilterGenre
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.network.services.ApiServiceOSM
import com.example.kinopedia.network.services.ApiServiceOverpass
import com.example.kinopedia.network.services.BASE_URL_OSM
import com.example.kinopedia.network.services.BASE_URL_OVERPASS
import com.example.kinopedia.network.services.client
import com.example.kinopedia.network.services.clientOSM
import com.example.kinopedia.network.services.clientOverpass
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.cinema.mapper.CinemaOSMToCinemaOSMModelMapper
import com.example.kinopedia.ui.cinema.mapper.CityOSMToCityOSMModelMapper
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel
import com.example.kinopedia.ui.cinema.model.CityOSMModel
import com.example.kinopedia.ui.favourite.mapper.FavouriteEntityToFavouriteEntityModelMapper
import com.example.kinopedia.ui.favourite.model.FavouriteEntityModel
import com.example.kinopedia.ui.film.mapper.ActorFilmPageToActorFilmPageMapper
import com.example.kinopedia.ui.film.mapper.ExternalSourceToExternalSourceModelMapper
import com.example.kinopedia.ui.film.mapper.KinopoiskFilmToKinopoiskFilmModelMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.filter.mapper.FilterCountryToFilterCountryModelMapper
import com.example.kinopedia.ui.filter.mapper.FilterGenreToFilterGenreModelMapper
import com.example.kinopedia.ui.filter.model.FilterCountryModel
import com.example.kinopedia.ui.filter.model.FilterGenreModel
import com.example.kinopedia.ui.home.mapper.FilmForAdapterToFilmForAdapterModelMapper
import com.example.kinopedia.ui.home.mapper.ThisMonthFilmToThisMonthFilmModelMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.utils.BASE_URL
import com.example.kinopedia.utils.LocationProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun provideKinopoiskFilmModuleMapper(
        mapper: KinopoiskFilmToKinopoiskFilmModelMapper
    ): BaseMapper<KinopoiskFilm, KinopoiskFilmModel>

    @Binds
    abstract fun provideFilmForAdapterModuleMapper(
        mapper: FilmForAdapterToFilmForAdapterModelMapper
    ): BaseMapper<FilmForAdapter, FilmForAdapterModel>

    @Binds
    abstract fun provideActorFilmPageToActorFilmPageMapper(
        mapper: ActorFilmPageToActorFilmPageMapper
    ): BaseMapper<ActorFilmPage, ActorFilmPageModel>

    @Binds
    abstract fun provideExternalSourceToExternalSourceModelMapper(
        mapper: ExternalSourceToExternalSourceModelMapper
    ): BaseMapper<ExternalSource, ExternalSourceModel>

    @Binds
    abstract fun provideThisMonthFilmToThisMonthFilmModelMapper(
        mapper: ThisMonthFilmToThisMonthFilmModelMapper
    ): BaseMapper<ThisMonthFilm, ThisMonthFilmModel>

    @Binds
    abstract fun provideFilterCountryToFilterCountryModelMapper(
        mapper: FilterCountryToFilterCountryModelMapper
    ): BaseMapper<FilterCountry, FilterCountryModel>

    @Binds
    abstract fun provideFilterGenreToFilterGenreModelMapper(
        mapper: FilterGenreToFilterGenreModelMapper
    ): BaseMapper<FilterGenre, FilterGenreModel>

    @Binds
    abstract fun provideFavouriteEntityToFavouriteEntityModelMapper(
        mapper: FavouriteEntityToFavouriteEntityModelMapper
    ): BaseMapper<FavouriteEntity, FavouriteEntityModel>

    @Binds
    abstract fun provideCityOSMToCityOSMModelMapper(
        mapper: CityOSMToCityOSMModelMapper
    ): BaseMapper<CityOSM, CityOSMModel>

    @Binds
    abstract fun provideCinemaOSMToCinemaOSMModelMapper(
        mapper: CinemaOSMToCinemaOSMModelMapper
    ): BaseMapper<CinemaOSM, CinemaOSMModel>
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FavouriteDatabase {
        return Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourite_database")
                    .fallbackToDestructiveMigration().build()
        }

    @Provides
    @Singleton
    fun provideRepository(database: FavouriteDatabase): FavouriteRepositoryImpl {
        return FavouriteRepositoryImpl(database.favouriteDao())
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: FavouriteDatabase): FavouriteDao {
        return database.favouriteDao()
    }

    @Provides
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider {
        return LocationProvider(context)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSimilarFilmsRepo(similarFilmsRepositoryImpl: FilmRepositoryImpl): FilmRepository

    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun provideMoreRepository(moreRepositoryImpl: MoreRepositoryImpl): MoreRepository

    @Binds
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun provideFilterRepository(filterRepositoryImpl: FilterRepositoryImpl): FilterRepository

    @Binds
    abstract fun provideCinemaRepository(cinemaRepositoryImpl: CinemaRepositoryImpl): CinemaRepository

    @Binds
    abstract fun provideFavouriteRepository(favouriteRepositoryImpl: FavouriteRepositoryImpl): FavouriteRepository
}

@Module
@InstallIn(ViewModelComponent::class)
class PagingModule {
    @Provides
    @Named("await")
    fun providesPagerAwait(
        apiService: ApiService,
        mapper: FilmForAdapterToFilmForAdapterModelMapper
    ): Pager<Int, FilmForAdapterModel> {
        return Pager(PagingConfig(20)) {
            MorePagingSourceAwait(apiService, mapper)
        }
    }

    @Provides
    @Named("popular")
    fun providesPagerPopular(
        apiService: ApiService,
        mapper: FilmForAdapterToFilmForAdapterModelMapper
    ): Pager<Int, FilmForAdapterModel> {
        return Pager(PagingConfig(20)) {
            MorePagingSource(apiService, mapper)
        }
    }

    @Provides
    @Named("premier")
    fun providesPagerPremier(
        apiService: ApiService,
        mapper: ThisMonthFilmToThisMonthFilmModelMapper
    ): Pager<Int, ThisMonthFilmModel> {
        return Pager(PagingConfig(20)) {
            MorePagingSourcePremier(apiService, mapper)
        }
    }

    @Provides
    @Named("TopFilms")
    fun providesPagerTopFilms(
        apiService: ApiService,
        mapper: FilmForAdapterToFilmForAdapterModelMapper
    ): Pager<Int, FilmForAdapterModel> {
        return Pager(PagingConfig(20)) {
            PagingSourceSearchTopFilms(apiService, mapper)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class NetworkModule {
        @Suppress("JSON_FORMAT_REDUNDANT")
        @Singleton
        @Provides
        @Named("retrofitKinopoisk")
        fun provideRetrofitKinopoiskClient(): Retrofit {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder().client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL).build()
        }

        @Singleton
        @Provides
        @Named("retrofitOSM")
        fun provideRetrofitOSMClient(): Retrofit {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder().client(clientOSM)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL_OSM).build()
        }

        @Singleton
        @Provides
        @Named("retrofitOverpass")
        fun provideRetrofitOverpassClient(): Retrofit {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder().client(clientOverpass)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL_OVERPASS).build()
        }

        @Provides
        fun provideApiService(@Named("retrofitKinopoisk") retrofit: Retrofit): ApiService =
            ApiService.create(retrofit)

        @Provides
        fun provideApiServiceOSM(@Named("retrofitOSM") retrofit: Retrofit): ApiServiceOSM =
            ApiServiceOSM.create(retrofit)

        @Provides
        fun provideApiServiceOverpass(@Named("retrofitOverpass") retrofit: Retrofit): ApiServiceOverpass =
            ApiServiceOverpass.create(retrofit)
    }
}