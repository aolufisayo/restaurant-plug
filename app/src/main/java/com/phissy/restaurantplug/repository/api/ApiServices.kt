package com.phissy.restaurantplug.repository.api

import androidx.lifecycle.LiveData
import com.phissy.restaurantplug.repository.api.network.Resource
import com.phissy.restaurantplug.repository.model.restaurants.BusinessResponse
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    @GET("businesses/search")
    fun searchRestaurant(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): LiveData<Resource<BusinessResponse>>

}