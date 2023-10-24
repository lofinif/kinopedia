package com.example.kinopedia.ui.favourite.view

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
import com.example.kinopedia.utils.FavouriteAdapterCallback
import com.example.kinopedia.utils.ItemOffsetDecoration
import com.example.kinopedia.utils.NavigationActionListener
import com.example.kinopedia.R
import com.example.kinopedia.data.database.FavouriteDatabase
import com.example.kinopedia.databinding.FragmentFavouriteBinding
import com.example.kinopedia.ui.favourite.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class FavouriteFragment : Fragment(), NavigationActionListener, FavouriteAdapterCallback {
    var filmId = -1
    private var adapterPosition = -1
    private lateinit var  itemTouchHelper : ItemTouchHelper
    @Inject
    lateinit var db : FavouriteDatabase
    private val sharedViewModel: FavouriteViewModel by activityViewModels()
    private val adapter = FavouriteAdapter(this, this)
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 15, 15)
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                lifecycleScope.launch(Dispatchers.IO) {
                    db.favouriteDao().deleteById(filmId)
                }

            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_navigation_favorite_to_filmPageFragment, bundle)
    }

    override fun updateFilmId(filmId: Int) {
        this.filmId = filmId
    }

    override fun updateAdapterPosition(adapterPosition: Int) {
        this.adapterPosition = adapterPosition
    }
}
