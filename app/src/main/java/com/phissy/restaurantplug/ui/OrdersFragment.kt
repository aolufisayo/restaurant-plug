package com.phissy.restaurantplug.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.adapter.OrdersAdapter
import com.phissy.restaurantplug.adapter.RestaurantAdapter
import com.phissy.restaurantplug.repository.model.Order

class OrdersFragment : Fragment(), RestaurantAdapter.OnItemClickListener {

    //    View model field....
    private var restaurantDetailViewModel: RestaurantViewModel? = null

    private lateinit var recyclerViewOrder: RecyclerView

    private lateinit var ordersAdapter: OrdersAdapter

    private var orderList = arrayListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        restaurantDetailViewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)

        recyclerViewOrder = view.findViewById(R.id.recyclerViewOrders)
        recyclerViewOrder.layoutManager = LinearLayoutManager(requireContext())
        ordersAdapter = OrdersAdapter(requireContext(), orderList,this)
        recyclerViewOrder.adapter = ordersAdapter

        getAllOrders()
        return view
    }

    private fun getAllOrders() {
        restaurantDetailViewModel!!.getMessages().observe(viewLifecycleOwner) {
            orderList.clear()
            orderList.addAll(it)
            ordersAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(position: Int) {
        restaurantDetailViewModel!!.deleteOrder(orderList[position])
        orderList.removeAt(position)
        ordersAdapter.notifyItemRemoved(position)
    }

}