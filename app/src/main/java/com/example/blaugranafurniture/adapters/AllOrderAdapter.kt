package com.example.blaugranafurniture.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.data.Order
import com.example.blaugranafurniture.data.OrderStatus
import com.example.blaugranafurniture.data.getOrderStatus
import com.example.blaugranafurniture.databinding.OrderItemBinding

class AllOrderAdapter: Adapter<AllOrderAdapter.OrdersViewHolder>() {
    inner class OrdersViewHolder (private val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.apply {
                tvOrderId.text = order.orderID.toString()
                tvOrderDate.text = order.date
                val resource = itemView.resources

                val colorDrawable = when (getOrderStatus(order.orderStatus)){
                    is OrderStatus.Ordered -> {
                        ColorDrawable(resource.getColor(R.color.g_yellow))
                    }
                    is OrderStatus.Canceled -> {
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                    is OrderStatus.Confirmed -> {
                        ColorDrawable(resource.getColor(R.color.g_orange_yellow))
                    }
                    is OrderStatus.Shipped -> {
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Delivered -> {
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Returned -> {
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                }

                imageOrderState.setImageDrawable(colorDrawable)
            }
        }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.products == newItem.products
        }
        override fun areContentsTheSame(oldItem:Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = differ.currentList[position]

        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Order) -> Unit)? = null
}