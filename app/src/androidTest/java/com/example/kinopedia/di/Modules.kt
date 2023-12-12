package com.example.kinopedia.di

import com.example.kinopedia.data.cinema.CinemaRepositoryImplTest
import com.example.kinopedia.data.favourite.FavouriteRepositoryImplTest
import com.example.kinopedia.data.film.FilmRepositoryImplTest
import com.example.kinopedia.data.filter.FilterRepositoryImplTest
import com.example.kinopedia.data.home.HomeRepositoryImplTest
import com.example.kinopedia.data.more.MoreRepositoryImplTest
import com.example.kinopedia.data.search.SearchRepositoryImplTest
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.kinopedia.domain.repository.FavouriteRepository
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [ViewModelComponent::class], replaces = [RepositoryModule::class])
abstract class TestRepositoryModule {
    @Binds
    abstract fun provideFilmRepository(similarFilmsRepositoryImpl: FilmRepositoryImplTest): FilmRepository

    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImplTest): HomeRepository

    @Binds
    abstract fun provideMoreRepository(moreRepositoryImpl: MoreRepositoryImplTest): MoreRepository

    @Binds
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImplTest): SearchRepository

    @Binds
    abstract fun provideFilterRepository(filterRepositoryImpl: FilterRepositoryImplTest): FilterRepository

    @Binds
    abstract fun provideCinemaRepository(cinemaRepositoryImpl: CinemaRepositoryImplTest): CinemaRepository

    @Binds
    abstract fun provideFavouriteRepository(favouriteRepositoryImpl: FavouriteRepositoryImplTest): FavouriteRepository

}