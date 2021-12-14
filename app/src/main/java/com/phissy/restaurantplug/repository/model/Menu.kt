package com.phissy.restaurantplug.repository.model

class Menu(
    val name: String, val description: String, val price: Double, val imageUrl: String,
    var  isChecked: Boolean
) {
}