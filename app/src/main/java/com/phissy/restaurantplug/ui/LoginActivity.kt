package com.phissy.restaurantplug.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.extensions.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ccpSignup.registerCarrierNumberEditText(editTextPhoneSignup)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) { // Needs permission

            requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS),
                PERMISSIONS_REQUEST_READ_SMS
            )

        } else { // Permission has already been granted

        }

        buttonSignup.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.buttonSignup -> {
                if (ccpSignup.isValidFullNumber) {
                    val bundle = Bundle()
                    bundle.putString("phone", ccpSignup.fullNumberWithPlus)
                    startActivity(VerificationCodeActivity::class.java, bundle)
                } else {
                    Toast.makeText(this, "Invalid phone number.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            PERMISSIONS_REQUEST_READ_SMS -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
            }
        }
    }

    companion object {
        const val PERMISSIONS_REQUEST_READ_SMS = 100
    }

}