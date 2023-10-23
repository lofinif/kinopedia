package com.example.kinopedia.ui.film

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.kinopedia.ItemOffsetDecoration
import com.example.kinopedia.R
import com.example.kinopedia.UpdateFilmCallBack
import com.example.kinopedia.data.FavouriteDao
import com.example.kinopedia.databinding.FragmentFilmPageBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FilmPageFragment : Fragment(), UpdateFilmCallBack{
    private val sharedViewModel: FilmPageViewModel by viewModels()
    private lateinit var binding: FragmentFilmPageBinding
    private val adapter = FilmPageSimilarAdapter(this)
    private val currentDate =
        LocalDateTime.now().format(DateTimeFormatter
            .ofPattern("d MMMM yyyy", Locale("ru", "ru")))
    @Inject
    lateinit var favouriteDao: FavouriteDao
    private var filmId = 0
    private val itemOffsetDecoration = ItemOffsetDecoration(30, 0, 30, 0)
    private val handler = android.os.Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmPageBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmId = arguments?.getInt("filmId")!!
        if (sharedViewModel.data.value?.kinopoiskId == null) {
            getFilm(filmId)
            Log.e("Get", filmId.toString())
        }
        bind()
        hidePremier()
        showPremierButton()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bind() {
        handler.postDelayed( {
            binding.scroll.fullScroll(ScrollView.FOCUS_UP)
        } ,5)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            recyclerViewSimilar.adapter = adapter
            recyclerViewActors.adapter = FilmPageAdapter()
            recyclerViewStaff.adapter = FilmPageAdapter()
            recyclerViewExternalSources.adapter = FilmPageExternalAdapter()
            recyclerViewSimilar.addItemDecoration(itemOffsetDecoration)
            recyclerViewActors.addItemDecoration(itemOffsetDecoration)
            recyclerViewStaff.addItemDecoration(itemOffsetDecoration)
            recyclerViewExternalSources.addItemDecoration(itemOffsetDecoration)
            notificationPremier.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Вы начали отслеживать: ${binding.nameMovie.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
            sharedViewModel.apply {
                data.observe(viewLifecycleOwner) {
                    val formattedString = getString(R.string.text_view_details_film, "${data.value?.displayYear}",
                        "${data.value?.displayGenres}", "${data.value?.displayFilmLength}", "${data.value?.displayCountry}")
                    ratingImdb.text = data.value?.displayRatingImdb
                    ratingKinopoisk.text = data.value?.displayRatingKinopoisk
                    yearMovie.text = formattedString
                    Picasso.get().load(getDataKinopoiskFilm().posterUrl).into(poster)
                    Picasso.get()
                        .load(getDataKinopoiskFilm().posterUrl)
                        .transform(BlurTransformation(requireContext(), 25, 3))
                        .into(posterBackground)
                    descriptionMovieMore.setOnClickListener {
                        if (descriptionMovieMore.maxLines == 4) {
                            descriptionMovieMore.maxLines = Integer.MAX_VALUE
                            TransitionManager.beginDelayedTransition(
                                descriptionMovieMore.parent as ViewGroup,
                                TransitionInflater.from(requireContext()).inflateTransition(R.transition.expand_animation)
                            )
                        } else descriptionMovieMore.maxLines = 4
                    }
                }
                dataSimilar.observe(viewLifecycleOwner) {
                    if (dataSimilar.value.isNullOrEmpty()) {
                        titleSimilarFilms.isGone = true
                    }
                    adapter.notifyDataSetChanged()
                }
                dataExternalSources.observe(viewLifecycleOwner) {
                    if (dataExternalSources.value.isNullOrEmpty())
                        titleExternal.isGone = true
                }
                titleStuff.isGone = true
                titleActors.isGone = true
                dataStaff.observe(viewLifecycleOwner) {
                    if (!dataStaff.value.isNullOrEmpty()) {
                        titleStuff.isGone = false
                        titleActors.isGone = false
                    }
                }
                data.observe(viewLifecycleOwner){
                    buttonCheck()
                }
            }
            buttonFavourite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (favouriteDao.checkId(sharedViewModel.data.value!!.kinopoiskId).first() > 0) {
                        deleteFavourite(sharedViewModel.data.value!!.kinopoiskId)
                        buttonUnpressed()
                    } else {
                        saveFavourite()
                        buttonPressed()
                    }
                }
            }
        }
    }

    private fun showPremierButton(){
        val premier = arguments?.getString("premier")
        binding.apply {
            if (premier != "unknown" && premier != null) {
                val premierDate = formatDate(premier)
                binding.premier.isVisible = true
                titlePremier.isVisible = true
                notificationPremier.isVisible = true
                binding.premier.text = premierDate
                if (compareDates(currentDate, premierDate) == 1) {
                    hidePremier()
                    titlePremier.isVisible = true
                    titlePremier.text = getString(R.string.premiere_took_place, premierDate)
                }
            }
        }
    }

    private fun buttonCheck(){
        CoroutineScope(Dispatchers.IO).launch {
            if (favouriteDao.checkId(sharedViewModel.data.value!!.kinopoiskId).first() > 0) {
                buttonPressed()
            } else  {
                buttonUnpressed()
            }
        }
    }
    private fun hidePremier() {
        binding.premier.isVisible = false
        binding.titlePremier.isVisible = false
        binding.notificationPremier.isVisible = false
    }

    private fun getFilm(kinopoiskId: Int) {
        sharedViewModel.apply {
            viewModelScope.launch {
                getFilmById(kinopoiskId)
            }
            getActors(kinopoiskId)
            getSimilarFilms(kinopoiskId)
            getExternalSources(kinopoiskId)
            dataSimilar.observe(viewLifecycleOwner){
                handler.postDelayed({
                    binding.apply {
                        recyclerViewSimilar.smoothScrollToPosition(0)
                        recyclerViewActors.smoothScrollToPosition(0)
                        recyclerViewStaff.smoothScrollToPosition(0)
                        recyclerViewExternalSources.smoothScrollToPosition(0)
                    }
                                    }, 75)
            }
        }
    }

    private fun updateFilm(kinopoiskId: Int) {
        val bundle = Bundle()
        bundle.putInt("filmId", kinopoiskId)
        findNavController().navigate(R.id.action_filmPageFragment_self, bundle)
        Log.e("kim", kinopoiskId.toString())
    }


    private fun buttonPressed(){
        binding.buttonFavourite.setBackgroundResource(R.drawable.button_pressed)
        binding.buttonFavourite.text = "Удалить из избранного"
    }

    private fun buttonUnpressed(){
        binding.buttonFavourite.setBackgroundResource(R.drawable.button_unpressed)
        binding.buttonFavourite.text = "В избранное"
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

    private fun saveFavourite() {
        val dateAdded = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formattedDateTime = dateAdded.format(formatter)

        sharedViewModel.data.value?.apply {
                sharedViewModel.saveRepository(
                    kinopoiskId,
                    posterUrl,
                    displayName,
                    displayYear,
                    displayCountry,
                    displayGenres,
                    displayOriginalName,
                    displayRatingKinopoisk,
                    displayRatingImdb,
                    displayDescription,
                    formattedDateTime
                )
        }
    }

    private fun deleteFavourite(filmId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            favouriteDao.deleteById(filmId)
        }
    }

    override fun update(filmId: Int) {
        updateFilm(filmId)
    }
}

private fun compareDates(dateString1: String, dateString2: String): Int {
    val format = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
    val date1: Date = format.parse(dateString1)
    val date2: Date = format.parse(dateString2)
    return date1.compareTo(date2)
}
