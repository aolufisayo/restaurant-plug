package com.phissy.restaurantplug.repository.model.restaurants

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (

	@SerializedName("address1") val address1 : String,
	@SerializedName("city") val city : String,
	@SerializedName("zip_code") val zip_code : String,
	@SerializedName("country") val country : String,
	@SerializedName("state") val state : String,
	@SerializedName("display_address") val display_address : List<String>
): Parcelable