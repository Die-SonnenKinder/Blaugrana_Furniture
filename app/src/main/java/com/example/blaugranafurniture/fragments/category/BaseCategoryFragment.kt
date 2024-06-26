package com.example.blaugranafurniture.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.adapters.BestProductsAdapter
import com.example.blaugranafurniture.databinding.FragmentBaseCategoryBinding
import com.example.blaugranafurniture.util.showBottomNavigationView

open class BaseCategoryFragment: Fragment(R.layout.fragment_base_category) {

    lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }
    protected val bestProductsAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRv()
        setupBestProductsRV()

        bestProductsAdapter.onClick = {
            val b = Bundle().apply{putParcelable("product",it)}
            findNavController().navigate(R.id.action_homeFragment_to_productsDetailsFragment,b)
        }
        offerAdapter.onClick = {
            val b = Bundle().apply{putParcelable("product",it)}
            findNavController().navigate(R.id.action_homeFragment_to_productsDetailsFragment,b)
        }

        binding.rvOffer.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dx !=0){
                    onOfferPagingRequest()
            }
            }
        })
        binding.nestedScrollBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{v,_,scrollY,_,_ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY){
                onBestProductPagingRequest()
            }
        })


    }
    fun showOfferLoading(){binding.offerProductsProgressbar.visibility = View.VISIBLE}
    fun hideOfferLoading(){binding.offerProductsProgressbar.visibility = View.GONE}
    fun showBestProductsLoading(){binding.offerProductsProgressbar.visibility = View.VISIBLE}
    fun hideBestProductsLoading(){binding.offerProductsProgressbar.visibility = View.GONE}

    open fun onOfferPagingRequest(){}
    open fun onBestProductPagingRequest(){}

    private fun setupBestProductsRV() {
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            adapter = bestProductsAdapter
        }
    }

    private fun setupOfferRv() {
        binding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}