package com.phissy.restaurantplug.data.remote

data class LocationX(
    val address1: String,
    val address2: Any,
    val address3: Any,
    val city: String,
    val country: String,
    val display_address: List<String>,
    val state: String,
    val zip_code: String
)