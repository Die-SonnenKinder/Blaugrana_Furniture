package com.example.blaugranafurniture.util


import android.view.View
import androidx.fragment.app.Fragment
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.blaugranafurniture.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.blaugranafurniture.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}