package com.phissy.restaurantplug.repository.model.restaurants

import com.google.gson.annotations.SerializedName
data class BusinessResponse (

	@SerializedName("businesses") val businesses : List<Businesses>,
	@SerializedName("total") val total : Int,
	@SerializedName("region") val region : Region
)