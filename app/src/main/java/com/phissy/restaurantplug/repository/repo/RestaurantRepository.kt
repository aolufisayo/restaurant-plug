package com.phissy.restaurantplug.repository.repo

import androidx.lifecycle.LiveData
import com.phissy.restaurantplug.repository.api.ApiServices
import com.phissy.restaurantplug.repository.api.network.NetworkResource
import com.phissy.restaurantplug.repository.api.network.Resource
import com.phissy.restaurantplug.repository.model.restaurants.BusinessResponse
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val apiServices: ApiServices
) {

    fun searchRestaurant(
        latitude: Double,
        longitude: Double
    ): LiveData<Resource<BusinessResponse>> {
        return object : NetworkResource<BusinessResponse>() {
            override fun createCall(): LiveData<Resource<BusinessResponse>> {
                return apiServices.searchRestaurant(
                    "Bearer _w8KSuJNqX-tcQJ4da_hKFDtbh6QtlhA-OQ5uVwN9kW1wJmKeMmw_1BxpAEMkFyqyynQKde_2vT9Pc_wDjT8EamfE9u0FYza7ZqXJK4cNI_rHKOA8WwWboBjSlTOXXYx",
                    latitude,
                    longitude
                )
            }
        }.asLiveData()
    }

}