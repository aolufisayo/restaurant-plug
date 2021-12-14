package com.phissy.restaurantplug.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.extensions.startActivityNewTask
import kotlinx.android.synthetic.main.activity_verification_code.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

class VerificationCodeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sweetAlertDialog: SweetAlertDialog

    //    Firebase fields...
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var phoneNumber: String

    private var verificationCode: String = ""

    private var isTimerRunning = false
    private var isCodeResendClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        sweetAlertDialog = SweetAlertDialog(this)
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait..."
        sweetAlertDialog.setCancelable(true)

        phoneNumber = intent.getStringExtra("phone")!!
        textViewPhoneNo.text = phoneNumber

        auth = FirebaseAuth.getInstance()

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                sweetAlertDialog.dismiss()
                textViewResendCode.visibility = View.VISIBLE
                Toast.makeText(this@VerificationCodeActivity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                sweetAlertDialog.dismiss()
                storedVerificationId = verificationId
                resendToken = token
                textViewResendCode.visibility = View.VISIBLE
                Toast.makeText(
                    this@VerificationCodeActivity,
                    "Verification code sent successfully.",
                    Toast.LENGTH_LONG
                ).show()
                if (isCodeResendClick) {
                    startTimer()
                }
            }
        }

        otpViewVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                verificationCode = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        startPhoneNumberVerification(phoneNumber)

        buttonVerifyCode.setOnClickListener(this)
        frameLayoutBackVerificationCode.setOnClickListener(this)
        textViewResendCode.setOnClickListener(this)


    }

    private val mServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val incomingMessage: String = intent.getStringExtra("incomingSms")!!
            if (incomingMessage.length > 6) {
                val code = incomingMessage.subSequence(0, 6)
                otpViewVerificationCode.setText(code.toString())
//                verifyPhoneNumberWithCode(this@VerificationCodeActivity.storedVerificationId,code.toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            if (mServiceReceiver != null) {
                unregisterReceiver(mServiceReceiver)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.SmsReceiver")
        registerReceiver(mServiceReceiver, filter)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.buttonVerifyCode -> {
                if (verificationCode.isNotEmpty() && verificationCode.length == 6) {
                    verifyPhoneNumberWithCode(storedVerificationId, verificationCode)
                }
            }
            R.id.frameLayoutBackVerificationCode -> onBackPressed()
            R.id.textViewResendCode -> {
                if (!isTimerRunning) {
                    if (resendToken != null) {
                        isCodeResendClick = true
                        resendVerificationCode(phoneNumber, resendToken)
                    } else {
                        startPhoneNumberVerification(phoneNumber)
                    }
                }
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        sweetAlertDialog.show()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        sweetAlertDialog.show()
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        sweetAlertDialog.show()
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
        if (token != null) {
            optionsBuilder.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivityNewTask(MainActivity::class.java)
                } else {
                    sweetAlertDialog.dismiss()
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun startTimer() {
        isTimerRunning = true
        val countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(second: Long) {
                textViewResendCode.text = "Send code again in: ${second / 1000}"
            }

            override fun onFinish() {
                isTimerRunning = false
                textViewResendCode.text = "Resend Code"
            }

        }
        countDownTimer.start()
    }

}