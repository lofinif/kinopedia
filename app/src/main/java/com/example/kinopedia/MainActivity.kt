package com.example.kinopedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.kinopedia.data.FavouriteDatabase
import com.example.kinopedia.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var db: FavouriteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FavouriteDatabase.getDatabase(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation = binding.bottomNavigation

        nav()
        bottomNavigation.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_search, R.id.navigation_favourite)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun nav() {

        bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home_graph -> {
                    if (navController.currentDestination?.id != item.itemId)
                        navController.navigate(R.id.navigation_home, null, navOptions {
                            popUpTo(R.id.navigation_home_graph)
                        })
                }

                R.id.navigation_search_graph -> {
                    if (navController.currentDestination?.id != item.itemId)
                        navController.navigate(R.id.navigation_search, null, navOptions {
                            popUpTo(R.id.navigation_home)
                        })
                }

                R.id.navigation_favourite_graph -> {
                    if (navController.currentDestination?.id != item.itemId)
                    navController.navigate(R.id.navigation_favourite, null, navOptions {
                        popUpTo(R.id.navigation_home)
                    })
                }
            }
        }
    }
}