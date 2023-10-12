package com.example.kinopedia.ui.filter

import com.example.kinopedia.R
import android.app.AlertDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.kinopedia.databinding.FragmentFilterBinding
import java.text.DecimalFormat


class FilterFragment : Fragment() {

    private val sharedViewModel: FilterViewModel by activityViewModels()
    private lateinit var binding: FragmentFilterBinding
    private lateinit var dialogYearPicker: AlertDialog
    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private var countryId = -1
    private var genreId = -1
    private var sortType = "RATING"
    private var type = "ALL"
    private var minRating = 0
    private var maxRating = 10
    private var selectedYearFrom = currentYear - 200
    private var selectedYearTo = currentYear
    private var selectedCountry = ""
    private var selectedGenre = ""
    private var selectedYears = ""
    private var selectedSort = ""
    private var page = 1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        resetButton()
        bind()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun resetButton(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_reset, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.reset -> {
                        reset()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun showYearPickerDialog() {
        val dialogView: View = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_year_picker, null)
        val yearPickerFrom: com.shawnlin.numberpicker.NumberPicker = dialogView.findViewById(R.id.number_picker_from)
        val yearPickerTo: com.shawnlin.numberpicker.NumberPicker = dialogView.findViewById(R.id.number_picker_to)
        val title = TextView(context)

        yearPickerFrom.minValue = currentYear - 100
        yearPickerFrom.maxValue = currentYear
        yearPickerFrom.value = currentYear

        yearPickerTo.minValue = currentYear - 100
        yearPickerTo.maxValue = currentYear
        yearPickerTo.value = currentYear


        title.text = "Год"
        title.setPadding(60, 40, 10, 10)
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        title.textSize = 16f

        val builder = AlertDialog.Builder(requireContext(), R.style.year_picker_style)
            .setView(dialogView)
            .setCustomTitle(title)
            .setPositiveButton("Выбрать") { dialog, _ ->
                selectedYearFrom = yearPickerFrom.value
                selectedYearTo = yearPickerTo.value
                selectedYears = "$selectedYearFrom - $selectedYearTo"
                binding.selectedYear.text = selectedYears
                dialog.dismiss()
            }
            .setNegativeButton("Сбросить") { dialog, _ ->
                selectedYearFrom = currentYear - 200
                selectedYearTo = currentYear
                binding.selectedYear.text = "любой"
                selectedYears = ""
                dialog.dismiss()
            }
        dialogYearPicker = builder.create()

        yearPickerFrom.setOnValueChangedListener { _, _, _ ->
            yearPickerFrom.maxValue = yearPickerTo.value
            yearPickerTo.minValue = yearPickerFrom.value
        }
        yearPickerTo.setOnValueChangedListener { _, _, _ ->
            yearPickerFrom.maxValue = yearPickerTo.value
            yearPickerTo.minValue = yearPickerFrom.value
        }

        dialogYearPicker.show()
        dialogYearPicker.window?.setLayout(850, 700)
        dialogYearPicker.window?.setBackgroundDrawableResource(R.drawable.year_picker_corner_dialog)
    }

    private fun rating(){
        binding.rangeSlider.setValues(0F, 10F)
        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            minRating = values[0].toInt()
            maxRating = values[1].toInt()
            if (minRating == 0 && maxRating == 10) binding.selectedRating.text = "неважно"
            else if (minRating == 0) binding.selectedRating.text = "до $maxRating"
            else if (maxRating == 10) binding.selectedRating.text = "от $minRating"
            else if (minRating == 0 && maxRating == 10) binding.selectedRating.text = "неважно"
            else binding.selectedRating.text = "от $minRating до $maxRating"
        }
           binding.rangeSlider.setLabelFormatter { value ->
            val numberFormat = DecimalFormat("#")
            numberFormat.format(value.toDouble())
        }
    }

    private fun country(){
        val countries = sharedViewModel.dataCountriesAndGenres.value?.countries
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_country_picker, null)
        val searchEditText = dialogView.findViewById<EditText>(R.id.searchEditText)
        val listView = dialogView.findViewById<ListView>(R.id.listView)
        val buttonResetCountries = dialogView.findViewById<Button>(R.id.button_reset_countries)

        if (countries.isNullOrEmpty()) {
            sharedViewModel.getCountriesAndGenres()
        }
        sharedViewModel.dataCountriesAndGenres.observe(viewLifecycleOwner) {
                    val countryNames = countries?.map { it.country }?.toTypedArray()
                    val countryIds = countries?.map { it.id }?.toTypedArray()
                    val filteredCountries = countryNames?.filter { it.isNotEmpty() }?.toTypedArray()
                    val map: Map<Int, String> = countryIds?.zip(countries.map { it.country })?.toMap() ?: emptyMap()
                    val sortedCountries = filteredCountries?.sorted()?.toTypedArray()

            sortedCountries?.let { names ->
                val adapter = ArrayAdapter(requireContext(), R.layout.list_view_countries_item, R.id.text_view_countries , names)
                listView.adapter = adapter

                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setView(dialogView)

                val alertDialog = alertDialogBuilder.create()

                searchEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) { adapter.filter.filter(s) } })
                listView.setOnItemClickListener { _, _, which, _ ->
                    selectedCountry = adapter.getItem(which).toString()
                    val selectedId = map.entries.find { it.value == selectedCountry }?.key
                    countryId = selectedId!!
                    binding.selectedCountry.text = selectedCountry
                    alertDialog.dismiss()
                }
                buttonResetCountries.setOnClickListener {
                    binding.selectedCountry.text = "любая"
                    countryId = -1
                    selectedCountry = ""
                    alertDialog.dismiss()
                }
                alertDialog.show()
                alertDialog?.window?.setBackgroundDrawableResource(R.drawable.corner_dialog_filters)
            }
         }
    }
    private fun showPopupMenu() {
        val wrapper: Context = ContextThemeWrapper(requireContext(), R.style.style_popup_menu)
        val popupMenu = PopupMenu(wrapper, binding.sortBy)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.option1 -> {
                    binding.sortBy.text = "рейтингу"
                    sortType = "RATING"
                    selectedSort = "рейтингу"
                    true
                }
                R.id.option2 -> {
                    binding.sortBy.text = "дате"
                    sortType = "YEAR"
                    selectedSort = "дате"
                    true
                }
                R.id.option3 -> {
                    binding.sortBy.text = "популярности"
                    sortType = "NUM_VOTE"
                    selectedSort = "популярности"
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showGenresPicker(){
         val genres = sharedViewModel.dataCountriesAndGenres.value?.genres
         val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_country_picker, null)
         val searchEditText = dialogView.findViewById<EditText>(R.id.searchEditText)
         val listView = dialogView.findViewById<ListView>(R.id.listView)
         val buttonResetGenres = dialogView.findViewById<Button>(R.id.button_reset_countries)

         if (genres.isNullOrEmpty()) {
             sharedViewModel.getCountriesAndGenres()
         }
         sharedViewModel.dataCountriesAndGenres.observe(viewLifecycleOwner) {
             val genre = genres?.map { it.genre }?.toTypedArray()
             val genreId = genres?.map { it.id }?.toTypedArray()
             val filteredGenres = genre?.filter { it.isNotEmpty() }?.toTypedArray()
             val map: Map<Int, String> = genreId?.zip(genres.map { it.genre })?.toMap() ?: emptyMap()
             val sortedGenres = filteredGenres?.sorted()?.toTypedArray()

             sortedGenres?.let { names ->
                 val adapter = ArrayAdapter(requireContext(), R.layout.list_view_countries_item, R.id.text_view_countries , names)
                 listView.adapter = adapter

                 val alertDialogBuilder = AlertDialog.Builder(requireContext())
                 alertDialogBuilder.setView(dialogView)

                 val alertDialog = alertDialogBuilder.create()

                 searchEditText.addTextChangedListener(object : TextWatcher {
                     override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                     override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                     override fun afterTextChanged(s: Editable?) { adapter.filter.filter(s) } })
                 listView.setOnItemClickListener { _, _, which, _ ->
                     selectedGenre = adapter.getItem(which).toString()
                     val selectedId = map.entries.find { it.value == selectedGenre }?.key
                     this.genreId = selectedId!!
                     binding.moreGenres.text = selectedGenre
                     alertDialog.dismiss()
                 }
                 buttonResetGenres.setOnClickListener {
                         binding.moreGenres.text = "все жанры"
                         this.genreId = -1
                         selectedGenre = ""

                     alertDialog.dismiss()
                 }
                 alertDialog.show()
                 alertDialog?.window?.setBackgroundDrawableResource(R.drawable.corner_dialog_filters)
             }
         }
     }

    private fun moreGenres(){
        binding.moreGenres.setOnClickListener {
        showGenresPicker()
        }
        binding.titleGenre.setOnClickListener {
        showGenresPicker()
        }
    }
    private fun sendFilters(){
        val countryIds = if (countryId == -1) null else arrayOf(countryId)
        val genreIds = if(genreId == -1) null else arrayOf(genreId)
        val bundle = Bundle().apply {
            putInt("countryId", countryIds?.get(0) ?: -1)
            putInt("genreId", genreIds?.get(0) ?: -1)
            putString("sortType", sortType)
            putString("type", type)
            putInt("minRating", minRating)
            putInt("maxRating", maxRating)
            putInt("selectedYearFrom", selectedYearFrom)
            putInt("selectedYearTo", selectedYearTo)
            putInt("page", page)
        }
        Log.i("minRT", minRating.toString())

            sharedViewModel.clearList()
            sharedViewModel.getFilmsByFiler(
                countryIds,
                genreIds,
                sortType,
                type,
                null,
                minRating,
                maxRating,
                selectedYearFrom,
                selectedYearTo,
                page
            )
            findNavController().navigate(R.id.action_filterFragment_to_filterResultFragment, bundle)

    }

    private fun reset() {
        binding.apply {
            countryId = -1
            selectedCountry.text = "любая"
            genreId = -1
            sortType = "RATING"
            sortBy.text = "рейтингу"
            minRating = 0
            maxRating = 10
            selectedRating.text = "неважно"
            selectedYearFrom = currentYear - 200
            selectedYearTo = currentYear
            moreGenres.text = "все жанры"
            selectedYear.text = "любой"
            rangeSlider.setValues(0f, 10f)
        }
        selectedCountry = ""
        selectedGenre = ""
        selectedYears = ""
        selectedSort = ""
    }



    fun bind(){
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = this
        moreGenres()
        rating()
        sharedViewModel.getCountriesAndGenres()
        binding.apply {
            changeYear.setOnClickListener { showYearPickerDialog() }
            selectedYear.setOnClickListener { showYearPickerDialog() }
            changeCountry.setOnClickListener { country() }
            selectedCountry.setOnClickListener { country() }
            sortBy.setOnClickListener{ showPopupMenu() }
            titleSortBy.setOnClickListener{ showPopupMenu() }
            titleToolbarReset.setOnClickListener{ reset() }
            backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        }
        binding.sendFilters.setOnClickListener {
            sendFilters()
        }
        if (selectedCountry.isNotEmpty()){
            binding.selectedCountry.text = selectedCountry
        }
        if (selectedGenre.isNotEmpty()){
            binding.moreGenres.text = selectedGenre
        }
        if (selectedYears.isNotEmpty()){
            binding.selectedYear.text = selectedYears
        }
        if (selectedSort.isNotEmpty()){
            binding.sortBy.text = selectedSort
        }
    }
}
