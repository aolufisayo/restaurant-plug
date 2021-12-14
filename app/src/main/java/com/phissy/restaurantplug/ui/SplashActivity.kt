package com.phissy.restaurantplug.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.extensions.startActivityWithFinish

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivityWithFinish(MainActivity::class.java)
            } else {
                startActivityWithFinish(LoginActivity::class.java)
            }
        }, 3000)

    }

}