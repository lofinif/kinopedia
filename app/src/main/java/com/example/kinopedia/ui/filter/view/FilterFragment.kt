package com.example.kinopedia.ui.filter.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentFilterBinding
import com.example.kinopedia.ui.filter.model.FilterSettings
import com.example.kinopedia.ui.filter.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private val sharedViewModel: FilterViewModel by activityViewModels()
    private lateinit var binding: FragmentFilterBinding
    private lateinit var dialogYearPicker: AlertDialog
    private val filterSettings = FilterSettings()
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

    private fun showYearPickerDialog() {
        val dialogView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_year_picker, null)
        val yearPickerFrom: com.shawnlin.numberpicker.NumberPicker =
            dialogView.findViewById(R.id.number_picker_from)
        val yearPickerTo: com.shawnlin.numberpicker.NumberPicker =
            dialogView.findViewById(R.id.number_picker_to)
        val title = TextView(context)

        yearPickerFrom.minValue = filterSettings.currentYear - 100
        yearPickerFrom.maxValue = filterSettings.currentYear
        yearPickerFrom.value = filterSettings.currentYear

        yearPickerTo.minValue = filterSettings.currentYear - 100
        yearPickerTo.maxValue = filterSettings.currentYear
        yearPickerTo.value = filterSettings.currentYear


        title.text = "Год"
        title.setPadding(60, 40, 10, 10)
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        title.textSize = 16f

        val builder = AlertDialog.Builder(requireContext(), R.style.year_picker_style)
            .setView(dialogView)
            .setCustomTitle(title)
            .setPositiveButton("Выбрать") { dialog, _ ->
                filterSettings.selectedYearFrom = yearPickerFrom.value
                filterSettings.selectedYearTo = yearPickerTo.value
                filterSettings.selectedYears =
                    "${filterSettings.selectedYearFrom} - ${filterSettings.selectedYearTo}"
                binding.selectedYear.text = filterSettings.selectedYears
                dialog.dismiss()
            }
            .setNegativeButton("Сбросить") { dialog, _ ->
                filterSettings.selectedYearFrom = filterSettings.currentYear - 200
                filterSettings.selectedYearTo = filterSettings.currentYear
                binding.selectedYear.text = "любой"
                filterSettings.selectedYears = ""
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


    private fun rating() {
        binding.rangeSlider.setValues(0F, 10F)
        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            filterSettings.minRating = values[0].toInt()
            filterSettings.maxRating = values[1].toInt()
            if (filterSettings.minRating == 0 && filterSettings.maxRating == 10) binding.selectedRating.text =
                "неважно"
            else if (filterSettings.minRating == 0) binding.selectedRating.text =
                "до ${filterSettings.maxRating}"
            else if (filterSettings.maxRating == 10) binding.selectedRating.text =
                "от ${filterSettings.minRating}"
            else if (filterSettings.minRating == 0 && filterSettings.maxRating == 10) binding.selectedRating.text =
                "неважно"
            else binding.selectedRating.text =
                "от ${filterSettings.minRating} до ${filterSettings.maxRating}"
        }
        binding.rangeSlider.setLabelFormatter { value ->
            val numberFormat = DecimalFormat("#")
            numberFormat.format(value.toDouble())
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
                    filterSettings.sortType = "RATING"
                    filterSettings.selectedSort = "рейтингу"
                    true
                }

                R.id.option2 -> {
                    binding.sortBy.text = "дате"
                    filterSettings.sortType = "YEAR"
                    filterSettings.selectedSort = "дате"
                    true
                }

                R.id.option3 -> {
                    binding.sortBy.text = "популярности"
                    filterSettings.sortType = "NUM_VOTE"
                    filterSettings.selectedSort = "популярности"
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showGenresPicker() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_country_picker, null)
        val searchEditText = dialogView.findViewById<EditText>(R.id.searchEditText)
        val listView = dialogView.findViewById<ListView>(R.id.listView)
        val buttonResetGenres = dialogView.findViewById<Button>(R.id.button_reset_countries)
        val alertDialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        sharedViewModel.flowGenres.observe(viewLifecycleOwner) {
            val genre = it?.map { it.genreName }?.toTypedArray()
            val genreId = it?.map { it.genreId }?.toTypedArray()
            val filteredGenres = genre?.filter { it.isNotEmpty() }?.toTypedArray()
            val map: Map<Int, String> = genreId?.zip(it.map { it.genreName })?.toMap() ?: emptyMap()
            val sortedGenres = filteredGenres?.sorted()?.toTypedArray()
            sortedGenres?.let { names ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_view_countries_item,
                    R.id.text_view_countries,
                    names
                )
                listView.adapter = adapter



                searchEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        adapter.filter.filter(s)
                    }
                })
                listView.setOnItemClickListener { _, _, which, _ ->
                    filterSettings.selectedGenre = adapter.getItem(which).toString()
                    val selectedId =
                        map.entries.find { it.value == filterSettings.selectedGenre }?.key
                    filterSettings.genreId = selectedId!!
                    binding.moreGenres.text = filterSettings.selectedGenre
                    alertDialog.dismiss()
                }
                buttonResetGenres.setOnClickListener {
                    binding.moreGenres.text = "все жанры"
                    filterSettings.genreId = -1
                    filterSettings.selectedGenre = ""

                    alertDialog.dismiss()
                }
                alertDialog?.window?.setBackgroundDrawableResource(R.drawable.corner_dialog_filters)
                alertDialog.show()
            }
        }
    }

    private fun moreGenres() {
        binding.moreGenres.setOnClickListener {
            showGenresPicker()
        }
        binding.titleGenre.setOnClickListener {
            showGenresPicker()
        }
    }

    private fun country() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_country_picker, null)
        val searchEditText = dialogView.findViewById<EditText>(R.id.searchEditText)
        val listView = dialogView.findViewById<ListView>(R.id.listView)
        val buttonResetCountries = dialogView.findViewById<Button>(R.id.button_reset_countries)
        val alertDialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        sharedViewModel.flowCountries.observe(viewLifecycleOwner) {
            val countryNames = it?.map { it.countryName }?.toTypedArray()
            val countryIds = it?.map { it.countryId }?.toTypedArray()
            val filteredCountries = countryNames?.filter { it.isNotEmpty() }?.toTypedArray()
            val map: Map<Int, String> =
                countryIds?.zip(it.map { it.countryName })?.toMap() ?: emptyMap()
            val sortedCountries = filteredCountries?.sorted()?.toTypedArray()

            sortedCountries?.let { names ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_view_countries_item,
                    R.id.text_view_countries,
                    names
                )
                listView.adapter = adapter

                searchEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        adapter.filter.filter(s)
                    }
                })
                listView.setOnItemClickListener { _, _, which, _ ->
                    filterSettings.selectedCountry = adapter.getItem(which).toString()
                    val selectedId =
                        map.entries.find { it.value == filterSettings.selectedCountry }?.key
                    filterSettings.countryId = selectedId!!
                    binding.selectedCountry.text = filterSettings.selectedCountry
                    alertDialog.dismiss()
                }
                buttonResetCountries.setOnClickListener {
                    binding.selectedCountry.text = "любая"
                    filterSettings.countryId = -1
                    filterSettings.selectedCountry = ""
                    alertDialog.dismiss()
                }
                alertDialog?.window?.setBackgroundDrawableResource(R.drawable.corner_dialog_filters)
                alertDialog.show()
            }
        }
    }

    private fun sendFilters() {
        sharedViewModel.updateFilterSettings(filterSettings)
        findNavController().navigate(R.id.action_filterFragment_to_filterResultFragment)
    }

    private fun reset() {
        binding.apply {
            selectedCountry.text = "любая"
            filterSettings.sortType = "RATING"
            sortBy.text = "рейтингу"
            selectedRating.text = "неважно"
            moreGenres.text = "все жанры"
            selectedYear.text = "любой"
            rangeSlider.setValues(0f, 10f)
            filterSettings.clearFilters()
        }
    }


    fun bind() {
        moreGenres()
        rating()
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = this
        binding.apply {
            changeYear.setOnClickListener { showYearPickerDialog() }
            selectedYear.setOnClickListener { showYearPickerDialog() }
            changeCountry.setOnClickListener { country() }
            selectedCountry.setOnClickListener { country() }
            sortBy.setOnClickListener { showPopupMenu() }
            titleSortBy.setOnClickListener { showPopupMenu() }
            titleToolbarReset.setOnClickListener { reset() }
            backButton.setOnClickListener { findNavController().popBackStack() }
        }
        binding.sendFilters.setOnClickListener {
            sendFilters()
        }
        if (filterSettings.selectedCountry.isNotEmpty()) {
            binding.selectedCountry.text = filterSettings.selectedCountry
        }
        if (filterSettings.selectedGenre.isNotEmpty()) {
            binding.moreGenres.text = filterSettings.selectedGenre
        }
        if (filterSettings.selectedYears.isNotEmpty()) {
            binding.selectedYear.text = filterSettings.selectedYears
        }
        if (filterSettings.selectedSort.isNotEmpty()) {
            binding.sortBy.text = filterSettings.selectedSort
        }
    }

    private fun resetButton() {
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
}
