package com.example.area.fragment.area

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.MainActivity
import com.example.area.utils.SessionManager
import com.google.android.material.textview.MaterialTextView

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        val ipPortUrl = (sessionManager.fetchAuthToken("url") ?: return null).dropLast(1).drop(7)

        getAndDisplayUserInfo(view)
        view.findViewById<MaterialTextView>(R.id.profile_current_ip_text_value).text = ipPortUrl
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.oauth_github_button).setOnClickListener {
            (context as AreaActivity).changeFragment(OAuthLinkingFragment(), "oauth_linking")
        }
        view.findViewById<Button>(R.id.profileLogoutButton).setOnClickListener {
            sessionManager.removeAuthToken("user_token");
            startActivity(Intent(context, MainActivity::class.java))
        }
        view.findViewById<Button>(R.id.profile_change_ip_port_button).setOnClickListener {
            (context as AreaActivity).changeFragment(ChangeIpPortFragment(), "change_ip_port")
        }
        return view
    }

    private fun getAndDisplayUserInfo(view: View) {
        val userInfo = ((context as AreaActivity).application as AREAApplication).userInfo ?: return

        view.findViewById<MaterialTextView>(R.id.profile_user_first_name_value).text = userInfo.firstName
        view.findViewById<MaterialTextView>(R.id.profile_user_last_name_value).text = userInfo.lastName
        view.findViewById<MaterialTextView>(R.id.profile_user_email_value).text = userInfo.email
    }
}