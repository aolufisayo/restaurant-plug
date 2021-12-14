package com.phissy.restaurantplug.repository.model.restaurants
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Businesses (

	@SerializedName("id") val id : String,
	@SerializedName("alias") val alias : String,
	@SerializedName("name") val name : String,
	@SerializedName("image_url") val image_url : String,
	@SerializedName("is_closed") val is_closed : Boolean,
	@SerializedName("url") val url : String,
	@SerializedName("review_count") val review_count : Int,
	@SerializedName("categories") val categories : List<Categories>,
	@SerializedName("rating") val rating : Double,
	@SerializedName("coordinates") val coordinates : Coordinates,
	@SerializedName("transactions") val transactions : List<String>,
	@SerializedName("price") var price : String? = "",
	@SerializedName("location") val location : Location,
	@SerializedName("phone") val phone : String,
	@SerializedName("display_phone") val display_phone : String,
	@SerializedName("distance") val distance : Double
): Parcelable