package com.phissy.restaurantplug.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.adapter.MenuAdapter
import com.phissy.restaurantplug.extensions.gone
import com.phissy.restaurantplug.extensions.visible
import com.phissy.restaurantplug.repository.model.Menu
import com.phissy.restaurantplug.repository.model.Order
import com.phissy.restaurantplug.repository.model.restaurants.Businesses
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.CameraUpdate


class RestaurantDetailActivity : AppCompatActivity(), MenuAdapter.OnMenuClickListener,
    OnMapReadyCallback {

    //    View model field....
    private var restaurantDetailViewModel: RestaurantViewModel? = null

    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var textViewRating: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var textViewTotalPrice: TextView
    private lateinit var map: GoogleMap

    private lateinit var businesses: Businesses

    private var menuList = arrayListOf<Menu>()

    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        restaurantDetailViewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
        imageView = findViewById(R.id.imageViewRestaurantDetail)
        textViewName = findViewById(R.id.textViewRestaurantName)
        textViewAddress = findViewById(R.id.textViewRestaurantAddress)
        textViewPrice = findViewById(R.id.textViewPrice)
        textViewRating = findViewById(R.id.textViewRating)
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice)

        if (intent != null) {
            businesses = intent.getParcelableExtra<Businesses>("restaurant") as Businesses
            Glide.with(this)
                .load(businesses.image_url)
                .centerCrop()
                .into(imageView)
            textViewName.text = businesses.name
            textViewRating.text = businesses.rating.toString()
            textViewPrice.text = businesses.price ?: "$$"
            textViewAddress.text =
                businesses.location.address1 + "\u00b7" + businesses.location.city

            recyclerViewMenu.layoutManager = LinearLayoutManager(this)

            menuList = restaurantDetailViewModel!!.getMenusList()
            recyclerViewMenu.setHasFixedSize(true)

            menuAdapter = MenuAdapter(this, menuList, this)
            recyclerViewMenu.adapter = menuAdapter

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment!!.getMapAsync(this)
        }

        findViewById<Button>(R.id.buttonCheckout).setOnClickListener {
            val selectedMenu = arrayListOf<Menu>()
            var totalPrice = 0.0
            for (menu in menuList) {
                if (menu.isChecked) {
                    selectedMenu.add(menu)
                    totalPrice += menu.price
                }
            }
            if (selectedMenu.size > 0) {
                var selectedMenusString = ""
                for (menu in selectedMenu) {
                    selectedMenusString += menu.name + ","
                }
                val order =
                    Order(0, totalPrice, businesses.name, businesses.image_url, selectedMenusString)
                restaurantDetailViewModel!!.setMessage(order)
                Toast.makeText(this, "Order placed successfully.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }

    }

    private fun showTotalPrice() {
        var totalPrice = 0.0
        for (menu in menuList) {
            if (menu.isChecked) {
                totalPrice += menu.price
            }
        }
        if (totalPrice != 0.0) {
            buttonCheckout.visible()
            textViewTotalPrice.visible()
        } else {
            buttonCheckout.gone()
            textViewTotalPrice.gone()
        }
        textViewTotalPrice.text = "${"$"}$totalPrice"
    }

    override fun onMenuClick(position: Int) {
        menuList[position].isChecked =
            !menuList[position].isChecked
        menuAdapter.notifyItemChanged(position)
        showTotalPrice()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latLng = LatLng(businesses.coordinates.latitude, businesses.coordinates.longitude)
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Your Location")
        )
        val location = CameraUpdateFactory.newLatLngZoom(
            latLng, 15f
        )
        map.animateCamera(location)
    }


}