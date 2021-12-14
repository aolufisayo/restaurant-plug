package com.phissy.restaurantplug.repository.model.restaurants

import com.google.gson.annotations.SerializedName

data class Region (

	@SerializedName("center") val center : Center
)