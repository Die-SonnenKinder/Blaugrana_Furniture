package com.example.blaugranafurniture.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.adapters.BillingProductAdapter
import com.example.blaugranafurniture.data.OrderStatus
import com.example.blaugranafurniture.data.OrderStatus.Canceled.status
import com.example.blaugranafurniture.data.getOrderStatus
import com.example.blaugranafurniture.databinding.FragmentOrderDetailBinding
import com.example.blaugranafurniture.util.VerticalItemDecroration

class OrderDetailFragment: Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingProductsAdapter by lazy { BillingProductAdapter() }
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order

        setupOrderRv()

        binding.apply {


            tvOrderId.text = "Order #${order.orderID}"


            val stepsList = mutableListOf(
                OrderStatus.Ordered.status,
                OrderStatus.Confirmed.status,
                OrderStatus.Shipped.status,
                OrderStatus.Delivered.status
            )

            when (order.orderStatus) {
                OrderStatus.Canceled.status -> {
                    stepsList[0] = OrderStatus.Canceled.status
                    stepsList[3] = OrderStatus.Delivered.status
                }
                OrderStatus.Returned.status -> {
                    stepsList.add(4, OrderStatus.Returned.status)
                }
            }

            binding.stepView.setSteps(stepsList)

            val currentOrderState = when (val status = getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                is OrderStatus.Returned -> 4

                else -> 0

            }


            stepView.go(currentOrderState, false)
            if (currentOrderState == 3 || currentOrderState == 4 || getOrderStatus(order.orderStatus) == OrderStatus.Canceled) {
                stepView.done(true)
            }

            tvFullName.text = order.address.fullName
            tvAddress.text = "${order.address.addressTitle} ${order.address.city}"
            tvPhoneNumber.text = order.address.phone

            tvTotalPrice.text = "$ ${order.totalPrice}"

        }

        billingProductsAdapter.differ.submitList(order.products)
        binding.imageCloseOrder.setOnClickListener {
            findNavController().navigate(R.id.action_orderDetailFragment_to_allOrderFragment)
        }
    }

    private fun setupOrderRv() {
        binding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecroration())
        }
    }
}