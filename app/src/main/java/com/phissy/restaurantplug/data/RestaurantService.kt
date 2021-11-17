package com.phissy.restaurantplug.data

import com.phissy.restaurantplug.data.remote.RestaurantDetailDto
import com.phissy.restaurantplug.data.remote.RestaurantsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantService {
    @GET("/search")
    suspend fun getRestaurants(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("location") location: String
    ): List<RestaurantsDto>

    @GET("/{id}")
    suspend fun getRestaurantDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        ): RestaurantDetailDto
}