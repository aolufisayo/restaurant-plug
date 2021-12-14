package com.phissy.restaurantplug.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.phissy.restaurantplug.repository.model.Menu
import com.phissy.restaurantplug.repository.model.Order
import com.phissy.restaurantplug.repository.repo.OrderRepository
import com.phissy.restaurantplug.repository.repo.RestaurantRepository
import javax.inject.Inject

class RestaurantViewModel(application: Application) :
    AndroidViewModel(application) {

    private var repository: OrderRepository = OrderRepository(application)

    val menuList = arrayListOf<Menu>()

    fun getMenusList(): ArrayList<Menu> {
        menuList.add(
            Menu(
                "Lasagna",
                "With butter lettuce, tomato and sauce bechamel",
                13.50,
                "https://media.istockphoto.com/photos/lasagna-on-a-square-white-plate-picture-id535851351?k=20&m=535851351&s=612x612&w=0&h=jdWOk9G_OOzHvPrvFrigqzk3EoucmIhUZr1-ey9NcGM=",
                false
            )
        )
        menuList.add(
            Menu(
                "Tandoori Chicken",
                "Amazing Indian dish with tenderloin chicken off the sizzles",
                19.20,
                "https://thumbs.dreamstime.com/b/tandoori-chicken-dish-prepared-roasting-marinated-yoghurt-spices-tandoor-cylindrical-clay-oven-indian-cuisine-full-155109609.jpg",
                false
            )
        )
        menuList.add(
            Menu(
                "Chilaquiles",
                "Chilaquiles with cheese and sauce. A delicious mexican dish",
                14.50,
                "https://www.saveur.com/uploads/2020/01/14/5K4HTZC7WBFUNCYR77AMTAKST4.jpg?auto=webp&width=785&height=588.75",
                false
            )
        )
        return menuList
    }

    fun getMessages() = repository.getAllOrders()

    fun setMessage(order: Order) {
        repository.setOrder(order)
    }

    fun deleteOrder(order: Order) = repository.deleteOrder(order)


}