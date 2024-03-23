package com.example.blaugranafurniture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.databinding.ActivityShoppingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.shoppingHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
    }
    //test2
}