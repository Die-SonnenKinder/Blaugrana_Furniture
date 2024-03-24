package com.example.blaugranafurniture.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.adapters.HomeviewpagerAdapter
import com.example.blaugranafurniture.databinding.FragmentHomeBinding
import com.example.blaugranafurniture.fragments.category.AccessoryFragment
import com.example.blaugranafurniture.fragments.category.ChairFragment
import com.example.blaugranafurniture.fragments.category.ClothesFragment
import com.example.blaugranafurniture.fragments.category.CupboardFragment
import com.example.blaugranafurniture.fragments.category.FurnitureFragment
import com.example.blaugranafurniture.fragments.category.MainCategoryFragment
import com.example.blaugranafurniture.fragments.category.TableFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    binding = FragmentHomeBinding.inflate(inflater)
    return binding.root
    }
    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment> (
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment(),
            ClothesFragment(),
        )

        val viewPager2Adapter = HomeviewpagerAdapter(categoriesFragments,childFragmentManager,lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text ="Main"
                1 -> tab.text ="Chair"
                2 -> tab.text ="Cupboard"
                3 -> tab.text ="Table"
                4 -> tab.text ="Accessories"
                5 -> tab.text ="Furniture"
                6 -> tab.text ="Clothes"
            }
        }.attach()
    }
}