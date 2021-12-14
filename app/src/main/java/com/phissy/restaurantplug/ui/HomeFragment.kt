package com.phissy.restaurantplug.ui

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.model.Place
import `in`.madapps.placesautocomplete.model.PlaceDetails
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phissy.restaurantplug.R
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.phissy.restaurantplug.adapter.RestaurantAdapter
import com.phissy.restaurantplug.di.base.Injectable
import com.phissy.restaurantplug.repository.model.restaurants.Businesses
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import android.location.Geocoder
import java.io.IOException
import java.util.*


class HomeFragment : Fragment(), Injectable, Listener, RestaurantAdapter.OnItemClickListener {

    //    View model fields....
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var homeViewModel: HomeViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var easyWayLocation: EasyWayLocation

    private var restaurantList = arrayListOf<Businesses>()

    private lateinit var restaurantAdapter: RestaurantAdapter

    private var livePlaceDetail = MutableLiveData<PlaceDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerViewRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        restaurantAdapter = RestaurantAdapter(requireContext(), restaurantList,this)
        recyclerView.adapter = restaurantAdapter

        easyWayLocation = EasyWayLocation(requireContext(), false, false, this)
        if (isPermissionGranted()) {
            easyWayLocation.startLocation()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }

         val placesApi = PlaceAPI.Builder().apiKey("AIzaSyBxu3l2UDTLYVuR51nA2vktvMCK0rws2Yw")
            .build(requireActivity())
        val autoCompleteTextView =
            view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewSearch)
        autoCompleteTextView.setAdapter(PlacesAutoCompleteAdapter(requireContext(), placesApi))
        autoCompleteTextView.threshold = 1
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val place = adapterView.getItemAtPosition(i) as Place
            autoCompleteTextView.setText(place.description)
            getLatLangFromAddress(place.description)
        }

        livePlaceDetail.observe(viewLifecycleOwner) {
            searchRestaurant(it!!.lat, it!!.lng)
        }

        return view
    }

    private fun getLatLangFromAddress(strAddress: String?) {
        val coder = Geocoder(requireContext(), Locale.getDefault())
        val address: List<Address>?
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
            } else {
                val location: Address = address[0]
                searchRestaurant(location.latitude, location.longitude)
            }
        } catch (e: IOException) {

        }
    }

    private fun isPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun locationOn() {

    }

    override fun currentLocation(location: Location?) {
        easyWayLocation.endUpdates()
        searchRestaurant(location?.latitude!!, location.longitude)
    }

    override fun locationCancelled() {

    }

    private fun searchRestaurant(latitude: Double, longitude: Double) {
        restaurantList.clear()
        homeViewModel.searchRestaurant(latitude, longitude)
            .observe(viewLifecycleOwner) {
                when {
                    it.status.isLoading() -> {

                    }
                    it.status.isSuccessful() -> {
                        if (it.data!!.businesses.isNotEmpty()) {
                            restaurantList.addAll(it.data!!.businesses)
                            restaurantAdapter.notifyDataSetChanged()
                        }
                    }
                    it.status.isError() -> {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    fun checkLocationPermission(isPermissionAllowed: Boolean) {
        if (isPermissionAllowed) {
            easyWayLocation.startLocation()
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireActivity(), RestaurantDetailActivity::class.java)
        intent.putExtra("restaurant", restaurantList[position])
        requireActivity().startActivityForResult(intent,200)
    }

}