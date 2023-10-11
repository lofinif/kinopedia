package com.example.kinopedia

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinopedia.data.FavouriteEntity
import com.example.kinopedia.network.ActorFilmPage
import com.example.kinopedia.network.ExternalSource
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.ThisMonthFilm
import com.example.kinopedia.ui.favourite.FavouriteAdapter
import com.example.kinopedia.ui.film.FilmPageAdapter
import com.example.kinopedia.ui.film.FilmPageExternalAdapter
import com.example.kinopedia.ui.film.FilmPageSimilarAdapter
import com.example.kinopedia.ui.filter.FilterResultAdapter
import com.example.kinopedia.ui.genre.GenreAdapter
import com.example.kinopedia.ui.home.HomeAdapter
import com.example.kinopedia.ui.home.HomeAdapterThisMonth
import com.example.kinopedia.ui.more.MoreAdapter
import com.example.kinopedia.ui.search.SearchAdapter
import com.example.kinopedia.ui.search.SearchResultAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Film>?) {
    val adapterRecommend = recyclerView.adapter as HomeAdapter
    adapterRecommend.submitList(data)
}

@BindingAdapter("listDataThisMonth")
fun bindRecyclerViewThisMonth(recyclerView: RecyclerView, data: List<ThisMonthFilm>?) {
    val adapter = recyclerView.adapter as HomeAdapterThisMonth
    adapter.submitList(data)
}
@BindingAdapter("listFavouriteFilms")
fun bindRecyclerViewFavouriteFilms(recyclerView: RecyclerView, data: List<FavouriteEntity>?) {
    val adapter = recyclerView.adapter as FavouriteAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataTopFilms")
fun bindRecyclerViewTopFilms(recyclerView: RecyclerView, data: List<Film>?) {
    val adapter = recyclerView.adapter as SearchAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataSimilar")
fun bindRecyclerViewSimilar(recyclerView: RecyclerView, data: List<Film>?) {
    val adapter = recyclerView.adapter as FilmPageSimilarAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataExternal")
fun bindRecyclerViewExternal(recyclerView: RecyclerView, data: List<ExternalSource>?) {
    val adapter = recyclerView.adapter as FilmPageExternalAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataActorAndStaff")
fun bindRecyclerViewActor(recyclerView: RecyclerView, data: List<ActorFilmPage>?) {
    val adapter = recyclerView.adapter as FilmPageAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataFilmsByKeyword")
fun bindRecyclerResultSearch(recyclerView: RecyclerView, data: List<KinopoiskFilm>?) {
    val adapter = recyclerView.adapter as SearchResultAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataFilmsByGenre")
fun bindRecyclerResultGenre(recyclerView: RecyclerView, data: List<KinopoiskFilm>?) {
    val adapter = recyclerView.adapter as GenreAdapter
    adapter.submitList(data)
}
@BindingAdapter("listDataFilmsByFilters")
fun bindRecyclerResultFilter(recyclerView: RecyclerView, data: List<KinopoiskFilm>?) {
    val adapter = recyclerView.adapter as FilterResultAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}
@BindingAdapter("loadingStatus")
fun bindStatus(statusImageView: ImageView, status: LoadingStatus) {

    when (status) {
        LoadingStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_screen)
        }

        LoadingStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.error_screen)
        }

        LoadingStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
    @BindingAdapter("loadingStatusElement")
fun bindStatusElement(statusImageView: ImageView, status: LoadingStatus) {

        when (status) {
            LoadingStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }

            LoadingStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
            }
            LoadingStatus.DONE -> {
                statusImageView.visibility = View.GONE
            }
        }
    }
