package com.phissy.restaurantplug.data.remote

import com.phissy.restaurantplug.domain.model.Restaurants

data class RestaurantsDto(
    val businesses: List<Businesse>,
    val region: Region,
    val total: Int
){
    fun toRestaurants(): Restaurants {
        return Restaurants(
            restaurants = businesses
        )
    }
}