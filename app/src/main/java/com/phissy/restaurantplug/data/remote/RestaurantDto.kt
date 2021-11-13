package com.phissy.restaurantplug.data.remote

data class RestaurantDto(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val display_phone: String,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val rating: Int,
    val review_count: Int,
    val transactions: List<Any>,
    val url: String
)