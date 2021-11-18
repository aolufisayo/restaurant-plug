package com.phissy.restaurantplug.data.repository

import com.phissy.restaurantplug.data.RestaurantService
import com.phissy.restaurantplug.data.remote.RestaurantDetailDto
import com.phissy.restaurantplug.data.remote.RestaurantsDto
import com.phissy.restaurantplug.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val api: RestaurantService
): RestaurantRepository {
    override suspend fun getAllRestaurants(token: String,term: String ,location: String): List<RestaurantsDto> {
        return api.getRestaurants(token, term, location)
    }

    override suspend fun getRestaurantById(token: String, id: String): RestaurantDetailDto {
        return api.getRestaurantDetail(token, id)
    }

}