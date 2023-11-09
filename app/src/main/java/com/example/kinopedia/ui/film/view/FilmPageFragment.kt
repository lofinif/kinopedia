package com.example.kinopedia.ui.film.view

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.kinopedia.R
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.databinding.FragmentFilmPageBinding
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.FilmForAdapterModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.film.state.FilmScreenState
import com.example.kinopedia.ui.film.viewmodel.FilmPageViewModel
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.UpdateFilmCallBack
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FilmPageFragment : Fragment(), UpdateFilmCallBack {
    @Inject
    lateinit var similarMapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>
    @Inject
    lateinit var actorAndStaffMapper: BaseMapper<ActorFilmPage, ActorFilmPageModel>
    @Inject
    lateinit var externalSourceMapper: BaseMapper<ExternalSource, ExternalSourceModel>

    private val sharedViewModel: FilmPageViewModel by viewModels()

    private lateinit var binding: FragmentFilmPageBinding
    private lateinit var similarAdapter : FilmPageSimilarAdapter
    private lateinit var actorAdapter : FilmPageAdapter
    private lateinit var staffAdapter : FilmPageAdapter
    private lateinit var externalAdapter : FilmPageExternalAdapter

    private val currentDate =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru", "ru")))
    private var filmId = 0
    private val itemOffsetDecoration = ItemOffsetDecoration(30, 0, 30, 0)
    private val handler = android.os.Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmPageBinding.inflate(inflater)
        similarAdapter = FilmPageSimilarAdapter(this, similarMapper)
        actorAdapter = FilmPageAdapter(actorAndStaffMapper)
        staffAdapter = FilmPageAdapter(actorAndStaffMapper)
        externalAdapter = FilmPageExternalAdapter(externalSourceMapper)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmId = arguments?.getInt("filmId")!!
        if (sharedViewModel.film.value?.kinopoiskId == null) {
            getFilm(filmId)
        }
        bind()
        observeViewModel()
        hidePremier()
        showPremierButton()
    }
    private fun observeViewModel() {
        sharedViewModel.screenState.observe(viewLifecycleOwner) { state ->
            binding.filmError.root.isVisible = state is FilmScreenState.Error
            binding.filmLoading.root.isVisible = state is FilmScreenState.Loading
            binding.filmContent.root.isVisible = state is FilmScreenState.Loaded

            if (state is FilmScreenState.Loaded) {
                bindModel(state.filmModel)
                actorAdapter.submitList(sharedViewModel.actorFilmPage.value)
                staffAdapter.submitList(sharedViewModel.staff.value)
                externalAdapter.submitList(sharedViewModel.externalSources.value)
                similarAdapter.submitList(sharedViewModel.similar.value?.items)
            }
        }
    }

    private fun bindModel(model: KinopoiskFilmModel){
        binding.filmContent.let {
           Picasso.get().load(model.posterUrl).into(it.poster)
            Picasso.get()
                .load(model.posterUrl)
                .transform(BlurTransformation(requireContext(), 25, 3))
                .into(it.posterBackground)

            it.nameMovie.text = model.displayName
            it.nameMovieOriginal.text = model.displayNameOriginal
            it.detailsMovie.text = model.displayDetails
            it.descriptionMovie.text = model.displayDescription
            it.ratingKinopoisk.text = model.displayRatingKinopoisk
            it.ratingImdb.text = model.displayRatingImdb

            it.titleExternal.isVisible = !sharedViewModel.externalSources.value.isNullOrEmpty()
            it.titleSimilarFilms.isVisible = !sharedViewModel.similar.value?.items?.isEmpty()!!
            it.titleActors.isVisible = !sharedViewModel.actorFilmPage.value.isNullOrEmpty()
            it.titleStuff.isVisible = !sharedViewModel.staff.value.isNullOrEmpty()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun bind() {
        handler.postDelayed( {
            binding.filmContent.scroll.fullScroll(ScrollView.FOCUS_UP)
        } ,5)

        binding.filmContent.apply {
            recyclerViewSimilar.adapter = similarAdapter
            recyclerViewActors.adapter = actorAdapter
            recyclerViewStaff.adapter = staffAdapter
            recyclerViewExternalSources.adapter = externalAdapter
            recyclerViewSimilar.addItemDecoration(itemOffsetDecoration)
            recyclerViewActors.addItemDecoration(itemOffsetDecoration)
            recyclerViewStaff.addItemDecoration(itemOffsetDecoration)
            recyclerViewExternalSources.addItemDecoration(itemOffsetDecoration)
            notificationPremier.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Вы начали отслеживать: ${binding.filmContent.nameMovie.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
            descriptionMovie.setOnClickListener {
                if (descriptionMovie.maxLines == 4) {
                    descriptionMovie.maxLines = Integer.MAX_VALUE
                    TransitionManager.beginDelayedTransition(
                        descriptionMovie.parent as ViewGroup,
                        TransitionInflater.from(requireContext())
                            .inflateTransition(R.transition.expand_animation)
                    )
                } else descriptionMovie.maxLines = 4
            }
            sharedViewModel.apply {

                film.observe(viewLifecycleOwner){
                    buttonCheck()
                }
            }
            buttonFavourite.setOnClickListener {
                sharedViewModel.checkAdd(filmId)
                sharedViewModel.isAdded.observe(viewLifecycleOwner) {
                    if (it) {
                        deleteFavourite(filmId)
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
                filmContent.notificationPremier.isVisible = true
                filmContent.titlePremier.isVisible = true
                filmContent.notificationPremier.isVisible = true
                filmContent.premier.text = premierDate
                if (compareDates(currentDate, premierDate) == 1) {
                    hidePremier()
                    filmContent.titlePremier.isVisible = true
                    filmContent.titlePremier.text = getString(R.string.premiere_took_place, premierDate)
                }
            }
        }
    }
    private fun buttonCheck(){
        sharedViewModel.checkButtonState(filmId)
        sharedViewModel.isFavourite.observe(viewLifecycleOwner) {
           if (it) {
               buttonPressed()
           } else {
               buttonUnpressed()
           }
       }
    }
    private fun hidePremier() {
        binding.filmContent.premier.isVisible = false
        binding.filmContent.titlePremier.isVisible = false
        binding.filmContent.notificationPremier.isVisible = false
    }
    private fun getFilm(kinopoiskId: Int) {
        sharedViewModel.apply {
            viewModelScope.launch {
                fetchFilm(kinopoiskId)
            }
            similar.observe(viewLifecycleOwner){
                handler.postDelayed({
                    binding.apply {
                        filmContent.recyclerViewSimilar.smoothScrollToPosition(0)
                        filmContent.recyclerViewActors.smoothScrollToPosition(0)
                        filmContent.recyclerViewStaff.smoothScrollToPosition(0)
                        filmContent.recyclerViewExternalSources.smoothScrollToPosition(0)
                    }
                                    }, 75)
            }
        }
    }
    private fun updateFilm(kinopoiskId: Int) {
        val bundle = Bundle()
        bundle.putInt("filmId", kinopoiskId)
        findNavController().navigate(R.id.action_filmPageFragment_self, bundle)
    }


    private fun buttonPressed(){
        binding.filmContent.buttonFavourite.setBackgroundResource(R.drawable.button_pressed)
        binding.filmContent.buttonFavourite.text = "Удалить из избранного"
    }

    private fun buttonUnpressed(){
        binding.filmContent.buttonFavourite.setBackgroundResource(R.drawable.button_unpressed)
        binding.filmContent.buttonFavourite.text = "В избранное"
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

        sharedViewModel.film.value?.apply {
                sharedViewModel.saveFavourite(
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
        sharedViewModel.deleteFavourite(filmId)
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