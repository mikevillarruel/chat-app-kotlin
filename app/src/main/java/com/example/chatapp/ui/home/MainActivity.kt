package com.example.chatapp.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.R
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        observeDestinationChange()

    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.individualChatFragment -> {
                    binding.bottomNavigation.hide()
                    window.statusBarColor = resources.getColor(R.color.toolbar)
                }
                else -> {
                    binding.bottomNavigation.show()
                    window.statusBarColor = Color.WHITE
                }
            }
        }
    }
}