package com.example.area.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.UserConnectionActivity

class NotLoggedInFragment : Fragment(R.layout.fragment_not_logged_in) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<Button>(R.id.not_logged_in_login_button).setOnClickListener {
            (context as UserConnectionActivity).changeFragment(LoginFragment(), "login")
        }
        view.findViewById<Button>(R.id.not_logged_in_register_button).setOnClickListener {
            (context as UserConnectionActivity).changeFragment(RegisterFragment(), "register")
        }
        return view
    }
}