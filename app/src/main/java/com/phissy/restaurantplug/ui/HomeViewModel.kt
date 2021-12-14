package com.phissy.restaurantplug.ui

import androidx.lifecycle.ViewModel
import com.phissy.restaurantplug.repository.repo.RestaurantRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val restaurantRepository: RestaurantRepository) :
    ViewModel() {

    fun searchRestaurant(
        latitude: Double,
        lontidude: Double
    ) = restaurantRepository.searchRestaurant(latitude, lontidude)

}