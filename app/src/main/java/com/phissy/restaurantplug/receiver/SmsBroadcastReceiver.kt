package com.phissy.restaurantplug.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast
import android.R.attr.phoneNumber


class SmsBroadcastReceiver : BroadcastReceiver() {

    val SMS = "android.provider.Telephony.SMS_RECEIVED"

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == SMS) {
            var body = ""
            val bundle = intent?.extras
            val pdusArr = bundle!!.get("pdus") as Array<Any>
            var messages: Array<SmsMessage?> = arrayOfNulls(pdusArr.size)

            // if SMSis Long and contain more than 1 Message we'll read all of them
            for (i in pdusArr.indices) {
                messages[i] = SmsMessage.createFromPdu(pdusArr[i] as ByteArray)
            }
            var MobileNumber: String? = messages[0]?.originatingAddress
//        Log.i(TAG, "MobileNumber =$MobileNumber")
            val bodyText = StringBuilder()
            for (i in messages.indices) {
                bodyText.append(messages[i]?.messageBody)
            }
            body = bodyText.toString()
            if (body.isNotEmpty()) {
                val i: Intent =
                    Intent("android.intent.action.SmsReceiver")
                i.putExtra("incomingSms", body)
                context!!.sendBroadcast(i)
//                Toast.makeText(context, body, Toast.LENGTH_LONG).show()
                // Do something, save SMS in DB or variable , static object or ....
//            Log.i("Inside Receiver :" , "body =$body")
            }
        }
    }

}