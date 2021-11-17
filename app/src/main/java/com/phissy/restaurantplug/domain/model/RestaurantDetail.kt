package com.phissy.restaurantplug.domain.model

import com.phissy.restaurantplug.data.remote.Category
import com.phissy.restaurantplug.data.remote.Coordinates
import com.phissy.restaurantplug.data.remote.Hour
import com.phissy.restaurantplug.data.remote.Location

data class RestaurantDetail(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val displayPhone: String,
    val hours: List<Hour>,
    val id: String,
    val imageUrl: String,
    val isClaimed: Boolean,
    val isClosed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val photos: List<String>,
    val rating: Int,
    val reviewCount: Int,
    val transactions: List<Any>,
    val url: String
)
