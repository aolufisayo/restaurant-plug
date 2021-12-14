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
import com.phissy.restaurantplug.repository.model.restaurants.Businesses
import java.math.RoundingMode

class RestaurantAdapter(
    val context: Context,
    val restaurantList: ArrayList<Businesses>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    class RestaurantHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var imageView: ImageView
        var textViewName: TextView
        var textViewRating: TextView

        init {
            imageView = item.findViewById(R.id.imageViewRestaurantRow)
            textViewName = item.findViewById(R.id.textViewNameRowRestaurant)
            textViewRating = item.findViewById(R.id.textViewRatingRowRestaurant)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        return RestaurantHolder(layoutInflater.inflate(R.layout.row_restaurant, parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        val restaurant = restaurantList[position]
        Glide.with(context)
            .load(restaurant.image_url)
            .centerCrop()
            .into(holder.imageView)
        holder.textViewName.text = restaurant.name
        holder.textViewRating.text =
            restaurant.rating.toBigDecimal().setScale(1, RoundingMode.UP).toDouble().toString()
        holder.item.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}