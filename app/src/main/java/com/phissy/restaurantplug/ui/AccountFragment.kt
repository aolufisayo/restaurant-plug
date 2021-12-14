package com.phissy.restaurantplug.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.phissy.restaurantplug.R
import com.phissy.restaurantplug.extensions.startActivityWithFinish
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        view.buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().startActivityWithFinish(LoginActivity::class.java)
        }
        return view
    }

}