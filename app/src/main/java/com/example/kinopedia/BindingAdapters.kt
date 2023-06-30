package com.example.kinopedia

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.ThisMonthFilm
import com.example.kinopedia.ui.home.HomeAdapter
import com.example.kinopedia.ui.home.HomeAdapterThisMonth

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

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}
