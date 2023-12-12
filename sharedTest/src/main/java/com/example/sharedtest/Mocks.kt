package com.example.sharedtest

import com.example.kinopedia.data.cinema.dto.CinemaOSM
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.network.models.AddressOSM
import com.example.kinopedia.network.models.Cinemas
import com.example.kinopedia.network.models.Countries
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.network.models.FilmsByGenre
import com.example.kinopedia.network.models.FilterCountry
import com.example.kinopedia.network.models.FilterGenre
import com.example.kinopedia.network.models.Genre
import com.example.kinopedia.network.models.GeometryOverpass
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.kinopedia.network.models.TagsOverpass
import com.example.kinopedia.network.models.ThisMonthFilms
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel
import com.example.kinopedia.ui.cinema.model.CityOSMModel
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel

val cityOSMMock
    get() = CityOSM(
        1,
        123.456,
        789.012,
        "Name",
        addressOSMMock
    )
val cityOSMModelMock
    get() = CityOSMModel(
        1,
        123.456,
        789.012,
        "Name",
        addressOSMMock
    )

val addressOSMMock
    get() = AddressOSM(
        "1",
        "road",
        "city",
        "village",
        "county",
        "state,",
        "region",
        "123456",
        "country",
        "789012"
    )

val cinemasMock
    get() = Cinemas(
        listOf(cinemaOSMMock)
    )

val cinemaOSMMock
    get() = CinemaOSM(
        1,
        123.454,
        789.011,
        "type",
        tagsOverpassMock,
        listOf(geometryOverpassMock)
    )

val cinemaOSMModelMock
    get() = CinemaOSMModel(
        1,
        123.456,
        789.012,
        tagsOverpassMock,
    )

val tagsOverpassMock
    get() = TagsOverpass(
        "name",
        "12345678",
        "website",
        "street",
        "housenumber",
        "city"
    )

val geometryOverpassMock
    get() = GeometryOverpass(
        123.456,
        789.012
    )

val kinopoiskFilmMock
    get() = KinopoiskFilm(
        1,
        "NameRu",
        "NameEn",
        "NameOriginal",
        1999,
        123,
        "description",
        99,
        "https://www.maycocolors.com/wp-content/uploads/2020/09/ss-141.jpg",
        "posterUrlPreview",
        listOf(genreMock),
        listOf(countriesMock),
        2.3,
        9.9
    )

val genreMock
    get() = Genre(
        "genre"
    )

val countriesMock
    get() = Countries(
        "country"
    )

val kinopoiskSimilarFilmsMock
    get() = KinopoiskSimilarFilms(
        listOf(filmForAdapterMock)
    )

val filmForAdapterMock
    get() = FilmForAdapter(
        1,
        "nameRu",
        "nameEn",
        "https://www.maycocolors.com/wp-content/uploads/2020/09/ss-141.jpg",
        "1999",
        "10",
        "posterUrlPreview",
        listOf(genreMock),
        listOf(countriesMock)
    )

val actorFilmPageMock
    get() = ActorFilmPage(
        1,
        "nameRu",
        "nameEn",
        "descriptionActor",
        "https://www.maycocolors.com/wp-content/uploads/2020/09/ss-141.jpg",
        "professionText",
        "professionKey",
    )

val externalSourcesMock
    get() = ExternalSources(
        1,
        listOf(externalSourceMock)
    )

val externalSourceMock
    get() = ExternalSource(
        "url",
        "platform",
        "https://www.maycocolors.com/wp-content/uploads/2020/09/ss-141.jpg",
        10,
        1,
        "author",
        "title",
        "description"
    )

val topFilmsMock
    get() = TopFilms(
        1,
        listOf(filmForAdapterMock)
    )

val thisMonthFilmMock
    get() = ThisMonthFilm(
        1,
        "nameRu",
        "nameEn",
        1999,
        "https://www.maycocolors.com/wp-content/uploads/2020/09/ss-141.jpg",
        "posterUrlPreview",
        listOf(genreMock),
        listOf(countriesMock),
        "premiereRu"
    )
val filterGenreMock
    get() = FilterGenre(
        1,
        "genre"
    )

val filterCountryMock
    get() = FilterCountry(
        1,
        "country"
    )

val kinopoiskFilmModelMock
    get() = KinopoiskFilmModel(
        1,
        "displayName",
        "displayOriginalName",
        "displayDetails",
        "displayFilmLength",
        "displayDescription",
        "posterUrl",
        "displayGenre",
        "displayCountries",
        "displayRatingKinopoisk",
        "displayRatingImdb",
        "displayYear"
    )

val filmsByGenreMock
    get() = FilmsByGenre(
        1,
        1,
        kinopoiskFilmListMock
    )

val filmForAdapterModelMock
    get() = FilmForAdapterModel(
        1,
        "displayName",
        "displayOriginalName",
        "displayGenre",
        "displayCountry",
        "displayRatingKinopoisk",
        "displayYear",
        "displayRatingImdb",
        "posterUrl"
    )

val thisMonthFilmModelMock
    get() = ThisMonthFilmModel(
        1,
        "displayName",
        "displayOriginalName",
        "displayGenre",
        "displayCountry",
        "displayYear",
        "posterUrl",
        "dash"
    )

val favouriteEntityMock
    get() = FavouriteEntity(
        1,
        "posterUrl",
        "nameRu",
        "year",
        "contry",
        "genre",
        "nameOriginal",
        "ratingKinopoisk",
        "ratingImdb",
        "description",
        "dateAdded"
    )

val thisMonthFilmsMock
    get() = ThisMonthFilms(
        1,
        listOf(thisMonthFilmMock)
    )

val actorFilmPageModelMock
    get() = ActorFilmPageModel(
        "displayName",
        "displayDescription",
        "posterUrl"
    )

val externalSourceModelMock
    get() = ExternalSourceModel(
        "url",
        "displayPlatform",
        "displayLogoUrl",
    )
val kinopoiskFilmModelListMock get() = listOf(kinopoiskFilmModelMock)
val kinopoiskFilmListMock get() = listOf(kinopoiskFilmMock)
val filmForAdapterModelListMock get() = listOf(filmForAdapterModelMock)

val thisMonthFilmModelListMock get() = listOf(thisMonthFilmModelMock)
val filmForAdapterModelListRepoMock
    get() = listOf(
        filmForAdapterModelMock.copy(displayName = "filmForAdapterMock1", displayGenre = "genre1"),
        filmForAdapterModelMock.copy(displayName = "filmForAdapterMock2", displayGenre = "genre2"),
        filmForAdapterModelMock.copy(displayName = "filmForAdapterMock3", displayGenre = "genre3")
    )

val topFilmsRepoMock
    get() = TopFilms(
        1,
        listOf(
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock1"),
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock2"),
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock3"),
        )
    )

val topFilmsRepoMock2
    get() = TopFilms(
        1,
        listOf(
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock4"),
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock5"),
            filmForAdapterMock.copy(nameRu = "filmForAdapterMock6"),
        )
    )
val thisMonthFilmsRepoMock
    get() = ThisMonthFilms(
        3,
        listOf(
            thisMonthFilmMock.copy(nameRu = "thisMonthFilmsMock1"),
            thisMonthFilmMock.copy(nameRu = "thisMonthFilmsMock2"),
            thisMonthFilmMock.copy(nameRu = "thisMonthFilmsMock3")
        )
    )

val actorFilmPageListRepoMock
    get() = listOf(
        actorFilmPageMock.copy(nameRu = "nameRuActor1", professionKey = "ACTOR"),
        actorFilmPageMock.copy(nameRu = "nameRuActor2", professionKey = "ACTOR"),
        actorFilmPageMock.copy(nameRu = "nameRuActor3", professionKey = "ACTOR"),
        actorFilmPageMock.copy(nameRu = "nameRuStaff4"),
        actorFilmPageMock.copy(nameRu = "nameRuStaff5"),
        actorFilmPageMock.copy(nameRu = "nameRuStaff6")
    )

val filtersRepoMock
    get() = Filters(
        listOf(
            filterGenreMock.copy(genre = "genre1"),
            filterGenreMock.copy(genre = "genre2"),
            filterGenreMock.copy(genre = "genre3")
        ),
        listOf(
            filterCountryMock.copy(country = "country1"),
            filterCountryMock.copy(country = "country2"),
            filterCountryMock.copy(country = "country3")
        )
    )