package com.phissy.restaurantplug.domain.repository

import com.phissy.restaurantplug.data.remote.RestaurantDetailDto
import com.phissy.restaurantplug.data.remote.RestaurantsDto

interface RestaurantRepository {
    suspend fun getAllRestaurants(token: String,term: String ,location: String): List<RestaurantsDto>

    suspend fun getRestaurantById(token: String,id: String): RestaurantDetailDto
}