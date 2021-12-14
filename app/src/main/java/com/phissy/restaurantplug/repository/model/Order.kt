package com.phissy.restaurantplug.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Order(
    @PrimaryKey(autoGenerate = true)
    var orderId: Int,
    var totalPrice: Double,
    var restaurantName: String,
    var restaurantImage: String,
    var selectedMenus: String
) {
}