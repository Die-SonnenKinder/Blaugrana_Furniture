package com.example.blaugranafurniture.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.activities.ShoppingActivity
import com.example.blaugranafurniture.adapters.ViewPager2Images
import com.example.blaugranafurniture.adapters.SizesAdapter
import com.example.blaugranafurniture.adapters.ColorsAdapter
import com.example.blaugranafurniture.databinding.FragmentProductDetailsBinding
import com.example.blaugranafurniture.util.hideBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductsDetailsFragment: Fragment(R.layout.fragment_product_details) {
    private val args by navArgs<ProductsDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizeRv()
        setupColorRv()
        setupViewPager()

        binding.imageClose.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

            if(product.colors.isNullOrEmpty())
                tvProductColors.visibility = View.INVISIBLE
            if(product.sizes.isNullOrEmpty())
                tvProductSize.visibility = View.INVISIBLE
        }

        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupViewPager(){
        binding.apply {
            viewPagerProductImages.adapter =viewPagerAdapter
        }
    }
    private fun setupColorRv(){
        binding.rvcolors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
    private fun setupSizeRv(){
        binding.rvSize.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
}