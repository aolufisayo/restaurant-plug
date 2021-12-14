package com.phissy.restaurantplug.repository.model.restaurants

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Categories (

	@SerializedName("alias") val alias : String,
	@SerializedName("title") val title : String
): Parcelable