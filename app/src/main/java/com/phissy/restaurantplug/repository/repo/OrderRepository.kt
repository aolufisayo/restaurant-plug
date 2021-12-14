package com.phissy.restaurantplug.repository.repo

import android.app.Application
import com.phissy.restaurantplug.repository.database.MyDatabase
import com.phissy.restaurantplug.repository.database.OrderDao
import com.phissy.restaurantplug.repository.model.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class OrderRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    private lateinit var orderDao: OrderDao

    init {
        val db = MyDatabase.getDatabase(application)
        orderDao = db!!.orderDao()
    }

    fun getAllOrders() = orderDao.getAllOrders()

    fun deleteOrder(order: Order) {
        launch {
            withContext(Dispatchers.IO) {
                orderDao.deleteData(order)
            }
        }
    }

    fun setOrder(order: Order) {
        launch { setOrderBG(order) }
    }

    private suspend fun setOrderBG(order: Order) {
        withContext(Dispatchers.IO) {
            orderDao.insertOrder(order)
        }
    }

}