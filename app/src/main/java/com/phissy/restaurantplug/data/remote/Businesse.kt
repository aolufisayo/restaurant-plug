package com.phissy.restaurantplug.data.remote

data class Businesse(
    val alias: String,
    val categories: List<CategoryX>,
    val coordinates: CoordinatesX,
    val display_phone: String,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: LocationX,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Int,
    val review_count: Int,
    val transactions: List<Any>,
    val url: String
)