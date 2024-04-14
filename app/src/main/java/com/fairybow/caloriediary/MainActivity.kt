package com.fairybow.caloriediary

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.fairybow.caloriediary.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fairybow.caloriediary.ui.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        navView = binding.navView
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setContentView(binding.root)
        setupNav()
    }

    private fun setupNav() {
        // Passing each menu ID as a set of IDs because each
        // one should be considered a top-level destination
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_day,
                R.id.navigation_check_in,
                R.id.navigation_history,
                R.id.navigation_settings
            )
        )

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemReselectedListener  { item ->
            val currentDestination = navController.currentDestination?.id
            if (currentDestination == null || currentDestination != item.itemId) {
                onSupportNavigateUp()
            }
        }

        // TODO: Generalize to all sub-fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_catalog -> {
                    navView.menu.findItem(R.id.navigation_settings).isChecked = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /*fun bottomNavHeight(): Int {
        return navView.height
    }*/
}
