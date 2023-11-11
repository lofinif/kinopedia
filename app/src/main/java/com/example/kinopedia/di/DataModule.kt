package com.example.kinopedia.di

import android.content.Context
import androidx.room.Room
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.database.FavouriteDatabase
import com.example.kinopedia.data.database.FavouriteDatabase.Companion.INSTANCE
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.repositories.FavouriteRepository
import com.example.kinopedia.data.film.FilmRepositoryImpl
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.home.HomeRepositoryImpl
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.network.models.ThisMonthFilm
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.network.services.client
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.mapper.ActorFilmPageToActorFilmPageMapper
import com.example.kinopedia.ui.film.mapper.ExternalSourceToExternalSourceModelMapper
import com.example.kinopedia.ui.film.mapper.FilmForAdapterToFilmForAdapterModelMapper
import com.example.kinopedia.ui.film.mapper.KinopoiskFilmToKinopoiskFilmModelMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.FilmForAdapterModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.mapper.FilmForAdapterToFilmForAdapterModelMapperHome
import com.example.kinopedia.ui.home.mapper.ThisMonthFilmToThisMonthFilmModelMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModelHome
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
    abstract fun provideFilmForAdapterModuleMapperHome(
        mapper: FilmForAdapterToFilmForAdapterModelMapperHome
    ): BaseMapper<FilmForAdapter, FilmForAdapterModelHome>
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
}


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FavouriteDatabase {
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourite_database")
                .fallbackToDestructiveMigration().build()
            INSTANCE = instance
            instance
        }
    }

    @Provides
    @Singleton
    fun provideRepository(database: FavouriteDatabase): FavouriteRepository {
        return FavouriteRepository(database.favouriteDao())
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: FavouriteDatabase): FavouriteDao {
        return database.favouriteDao()
    }

    @Provides
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider{
        return LocationProvider(context)
    }

}
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun provideSimilarFilmsRepo(similarFilmsRepositoryImpl: FilmRepositoryImpl): FilmRepository

    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}



@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Suppress("JSON_FORMAT_REDUNDANT")
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = ApiService.create(retrofit)
}