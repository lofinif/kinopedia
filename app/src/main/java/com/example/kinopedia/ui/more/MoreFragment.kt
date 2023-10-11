package com.example.kinopedia.ui.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopedia.ItemOffsetDecoration
import com.example.kinopedia.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private val sharedViewModel: MoreViewModel by viewModels()
    private lateinit var binding: FragmentMoreBinding
    private val itemOffsetDecoration = ItemOffsetDecoration(0, 0, 30, 0)
    private val adapter = MoreAdapter()
    private var isLoaded = false
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()

    }

    private fun comingSoon(){
        sharedViewModel.getAwaitFilms(currentPage)
        binding.recyclerViewMore.addOnScrollListener(listenerComingSoon)
        sharedViewModel.coming.observe(viewLifecycleOwner){adapter.addAllTrendingAndAwait(it)}
    }

    private fun thisMonth(){
        sharedViewModel.getFilmsThisMonth(currentPage)
        binding.recyclerViewMore.addOnScrollListener(listenerComingThisMonth)
        sharedViewModel.thisMonth.observe(viewLifecycleOwner){ adapter.addAllComingThisMonth(it) }

    }

    private fun trending() {
        sharedViewModel.getPopularFilms(currentPage)
        binding.recyclerViewMore.addOnScrollListener(listenerTrending)
        sharedViewModel.trending.observe(viewLifecycleOwner) { adapter.addAllTrendingAndAwait(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNextItemsTrending(page: Int) {
        sharedViewModel.loadNextTrending(page)
        sharedViewModel.trending.observe(viewLifecycleOwner) {
            sharedViewModel.trending.value?.let {
                        adapter.addAllTrendingAndAwait(it)}
                binding.recyclerViewMore.post {
                    adapter.notifyDataSetChanged()
                }
            }
            Handler(Looper.getMainLooper()).postDelayed({
                isLoaded = false
            }, 1500)
        }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadNextItemsComingSoon(page: Int) {
        sharedViewModel.loadNextComingSoon(page)
        sharedViewModel.coming.observe(viewLifecycleOwner) {
            sharedViewModel.coming.value?.let { adapter.addAllTrendingAndAwait(it) }
            binding.recyclerViewMore.post{
                adapter.notifyDataSetChanged()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
          isLoaded = false
        }, 500)
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadNextItemsComingThisMonth(page: Int) {
        sharedViewModel.loadNextComingThisMonth(page)
        sharedViewModel.thisMonth.observe(viewLifecycleOwner) {
            sharedViewModel.thisMonth.value?.let { adapter.addAllComingThisMonth(it) }
            binding.recyclerViewMore.post{
                adapter.notifyDataSetChanged()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
          isLoaded = false
        }, 500)
    }

    private val listenerTrending = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (!isLoaded) {
                if (lastVisibleItemPosition == totalItemCount - 1 && currentPage < sharedViewModel.pageCountTrendingAndAwait) {
                    isLoaded = true
                    currentPage++
                    loadNextItemsTrending(currentPage)
                }
            }
        }
    }
    private val listenerComingSoon = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (!isLoaded) {
                if (lastVisibleItemPosition == totalItemCount - 1 && currentPage < sharedViewModel.pageCountTrendingAndAwait) {
                    isLoaded = true
                    currentPage++
                    loadNextItemsComingSoon(currentPage)
                }
            }
        }
    }
    private val listenerComingThisMonth = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (!isLoaded) {
                if (lastVisibleItemPosition == totalItemCount - 1 && currentPage < sharedViewModel.pageCountThisMonth) {
                    isLoaded = true
                    currentPage++
                    loadNextItemsComingThisMonth(currentPage)
                }
            }
        }
    }

    private fun bind() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            recyclerViewMore.adapter = adapter
            backButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            recyclerViewMore.addItemDecoration(itemOffsetDecoration)
        }
        when (arguments?.getString("more_type")) {
            "Coming soon" -> comingSoon()
            "Trending" -> trending()
            "Coming this month" -> thisMonth()
        }
    }
}