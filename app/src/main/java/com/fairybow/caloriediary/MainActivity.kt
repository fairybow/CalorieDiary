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
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)

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

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getBottomNav(): BottomNavigationView {
        return binding.navView
    }
}
