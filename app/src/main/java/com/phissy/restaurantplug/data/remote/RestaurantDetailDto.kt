package com.phissy.restaurantplug.data.remote

import com.phissy.restaurantplug.domain.model.RestaurantDetail

data class RestaurantDetailDto(
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val display_phone: String,
    val hours: List<Hour>,
    val id: String,
    val image_url: String,
    val is_claimed: Boolean,
    val is_closed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val photos: List<String>,
    val rating: Int,
    val review_count: Int,
    val transactions: List<Any>,
    val url: String
){
    fun toRestaurantDetail(): RestaurantDetail {
        return RestaurantDetail(
            alias = alias,
            categories = categories,
            coordinates = coordinates,
            displayPhone = display_phone,
            hours = hours,
            id = id,
            imageUrl = image_url,
            isClaimed = is_claimed,
            isClosed = is_closed,
            location = location,
            name = name,
            phone = phone,
            photos = photos,
            rating = rating,
            reviewCount = review_count,
            transactions = transactions,
            url = url
        )
    }
}