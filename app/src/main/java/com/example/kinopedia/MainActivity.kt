package com.example.kinopedia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.kinopedia.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation = binding.bottomNavigation


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_graph,
                R.id.navigation_search_graph,
                R.id.navigation_favourite_graph
            )
        )
        navController.navigateUp(appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
        nav()
        navListener()
    }

    private fun nav() {
        bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_graph -> {
                    if (navController.currentDestination?.id == R.id.navigation_home) {
                    } else if (navController.currentDestination?.id != item.itemId)
                        navController.navigate(R.id.navigation_home, null, navOptions {
                            popUpTo(R.id.navigation_home_graph)
                        })
                }

                R.id.navigation_search_graph -> {
                    if (navController.currentDestination?.id != item.itemId)
                        navController.navigate(R.id.navigation_search, null, navOptions {
                            popUpTo(R.id.navigation_search_graph)
                        })
                }

                R.id.navigation_favourite_graph -> {
                    if (navController.currentDestination?.id != item.itemId)
                        navController.navigate(R.id.navigation_favourite, null, navOptions {
                            popUpTo(R.id.navigation_favourite_graph)
                        })
                }
            }
        }
    }

    private fun navListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nearestCinemaFragment
                || destination.id == R.id.filterFragment
                || destination.id == R.id.cinemaWelcomeFragment
            ) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}