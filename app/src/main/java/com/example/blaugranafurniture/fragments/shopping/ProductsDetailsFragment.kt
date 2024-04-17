package com.example.blaugranafurniture.fragments.shopping

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.adapters.ColorsAdapter
import com.example.blaugranafurniture.adapters.SizesAdapter
import com.example.blaugranafurniture.adapters.ViewPager2Images
import com.example.blaugranafurniture.data.CartProduct
import com.example.blaugranafurniture.databinding.FragmentProductDetailsBinding
import com.example.blaugranafurniture.util.Resource
import com.example.blaugranafurniture.util.hideBottomNavigationView
import com.example.blaugranafurniture.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductsDetailsFragment: Fragment(R.layout.fragment_product_details) {
    private val args by navArgs<ProductsDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColors: Int? =null
    private var selectedSize: String? =null
    private val viewModel by viewModels<DetailViewModel>()

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
                findNavController().navigate(R.id.action_productsDetailsFragment_to_homeFragment)
        }

        sizesAdapter.onItemClick = {selectedSize=it}
        colorsAdapter.onItemClick = {selectedColors=it}

        binding.buttonAddToCart.setOnClickListener {
            if (product.colors.isNullOrEmpty() || product.sizes.isNullOrEmpty()) {
                viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColors, selectedSize))
            } else {
                if (selectedColors == null || selectedSize == null) {
                    Toast.makeText(requireContext(), "Please select color and size", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColors, selectedSize))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it){
                    is Resource.Loading -> {
                        binding.buttonAddToCart.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonAddToCart.revertAnimation()
                    }
                    is Resource.Error -> {
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description
            product.offerPercentage?.let {
                val offerPercentageDecimal = it / 100.0
                val priceAfterOffer = product.price - (product.price * offerPercentageDecimal)
                tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                tvProductPrice.paintFlags = tvNewPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            if(product.offerPercentage == null)
                tvNewPrice.visibility = View.INVISIBLE

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
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }
    private fun setupColorRv(){
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        }
    }
    private fun setupSizeRv(){
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        }
    }


}