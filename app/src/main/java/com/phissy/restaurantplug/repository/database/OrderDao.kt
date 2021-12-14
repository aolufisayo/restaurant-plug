package com.phissy.restaurantplug.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.phissy.restaurantplug.repository.model.Order

@Dao
interface OrderDao {

    @Insert
    fun insertOrder(order: Order)

    @Query("SELECT * from `order` ORDER BY orderId ASC")
    fun getAllOrders(): LiveData<List<Order>>

    @Delete
    fun deleteData(order: Order)

}