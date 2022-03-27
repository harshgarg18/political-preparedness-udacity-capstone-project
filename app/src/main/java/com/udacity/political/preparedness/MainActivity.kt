package com.udacity.political.preparedness

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.udacity.political.preparedness.databinding.ActivityMainBinding
import com.udacity.political.preparedness.util.allowMultiLineText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.toolbar.allowMultiLineText()
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
