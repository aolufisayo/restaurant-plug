package com.phissy.restaurantplug.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.repository.model.Order

class OrdersAdapter(val context: Context, val orderList: ArrayList<Order>,
val onItemClickListener: RestaurantAdapter.OnItemClickListener) :
    RecyclerView.Adapter<OrdersAdapter.OrdersHolder>() {

    var layoutInflater = LayoutInflater.from(context)

    class OrdersHolder(item: View) : RecyclerView.ViewHolder(item) {

        var imageView: ImageView
        var textViewName: TextView
        var textViewMenu: TextView
        var imageViewDelete: ImageView
        var textViewPrice: TextView

        init {
            imageView = item.findViewById(R.id.imageViewRestaurantRowOrder)
            textViewName = item.findViewById(R.id.textViewRestaurantNameRowOrder)
            textViewPrice = item.findViewById(R.id.textViewPriceRowOrder)
            textViewMenu = item.findViewById(R.id.textViewMenusRowOrder)
            imageViewDelete = item.findViewById(R.id.imageViewDeleteOrder)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolder {
        return OrdersHolder(layoutInflater.inflate(R.layout.row_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrdersHolder, position: Int) {
        Glide.with(context)
            .load(orderList[position].restaurantImage)
            .centerCrop()
            .into(holder.imageView)
        holder.textViewMenu.text = orderList[position].selectedMenus
        holder.textViewName.text = orderList[position].restaurantName
        holder.textViewPrice.text = "Total Price: ${orderList[position].totalPrice}"

        holder.imageViewDelete.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}