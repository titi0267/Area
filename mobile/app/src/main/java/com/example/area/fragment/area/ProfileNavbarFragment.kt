package com.example.area.fragment.area

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.MainActivity
import com.example.area.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileNavbarFragment : Fragment(R.layout.fragment_profile_on_click) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<BottomNavigationView>(R.id.area_profile_navbar).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile_navbar_logout_item -> {
                    val sessionManager = SessionManager(context as AreaActivity)
                    sessionManager.removeAuthToken("user_token")
                    startActivity(Intent(context, MainActivity::class.java))
                    true
                }
                R.id.profile_navbar_services_item -> {
                    (context as AreaActivity).changeFragment(OAuthLinkingFragment(), "oauth_linking")
                    true
                }
                R.id.profile_navbar_profile_item -> {
                    (context as AreaActivity).changeFragment(ProfileFragment(), "profile")
                    true
                }
                else -> false
            }
        }
        return view
    }
}