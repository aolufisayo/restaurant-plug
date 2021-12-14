package com.phissy.restaurantplug.repository.model.restaurants

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates (

	@SerializedName("latitude") val latitude : Double,
	@SerializedName("longitude") val longitude : Double
): Parcelable