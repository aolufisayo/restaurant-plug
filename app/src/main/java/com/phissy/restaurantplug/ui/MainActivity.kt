package com.phissy.restaurantplug.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.View
import com.phissy.restaurantplug.R

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup navHostFragment with bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        this.navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(this.navController)

    }

    fun orderPlaced() {
        val view: View = bottomNavigationView.findViewById(R.id.ordersFragment)
        view.performClick()
        Handler().postDelayed({
            navController.navigate(R.id.ordersFragment)
        }, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                orderPlaced()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            navHost?.let { navFragment ->
                navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                    (fragment as HomeFragment).checkLocationPermission(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                }
            }
        }
    }
}