package com.example.kinopedia.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.FavouriteApplication
import com.example.kinopedia.ItemOffsetDecoration
import com.example.kinopedia.MainActivity
import com.example.kinopedia.NavigationActionListener
import com.example.kinopedia.R
import com.example.kinopedia.databinding.FragmentFavouriteBinding
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment(), NavigationActionListener {
    var filmId = -1
    var adapterPosition = -1
    private lateinit var  itemTouchHelper : ItemTouchHelper
    lateinit var mainActivity : MainActivity
    private val sharedViewModel: FavouriteViewModel by activityViewModels{
        FavouriteViewModel.FavouriteFactory((requireContext().applicationContext
                as FavouriteApplication).database.favouriteDao())
    }
    private val adapter = FavouriteAdapter(this)
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 15, 15)
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bind(){
        itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFavourite)
        binding.apply {
            recyclerViewFavourite.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
            recyclerViewFavourite.addItemDecoration(itemOffsetDecoration)
        }
        sharedViewModel.allFilms.observe(viewLifecycleOwner) {
            sharedViewModel.updatedList = sharedViewModel.allFilms.value?.toMutableList()
            adapter.submitList(sharedViewModel.updatedList)
        }
    }

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }
        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.LEFT) {
                sharedViewModel.updatedList?.removeAt(position)
                sharedViewModel.updateData()
                adapter.notifyDataSetChanged()
                lifecycleScope.launch {
                    mainActivity.db.favouriteDao().deleteById(filmId)
                }

            }
        }
    }

    override fun navigateToFilmPage(bundle: Bundle) {
        findNavController().navigate(R.id.action_navigation_favorite_to_filmPageFragment, bundle)
    }
}
