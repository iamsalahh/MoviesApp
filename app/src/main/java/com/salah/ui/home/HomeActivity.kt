package com.salah.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.salah.R
import com.salah.base.BaseActivity
import com.salah.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupNavigationController();
    }

    private fun setupNavigationController() {

        val navController = findNavController(R.id.nav_movies_host_fragment)
        navController.setGraph(R.navigation.navigation_popular, intent.extras)
    }
}
