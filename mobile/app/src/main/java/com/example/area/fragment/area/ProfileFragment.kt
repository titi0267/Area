package com.example.area.fragment.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.MainActivity
import com.example.area.model.UserInfo
import com.example.area.repository.Repository
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

        getAndDisplayUserInfo(view)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.profileLogoutButton).setOnClickListener {
            sessionManager.removeAuthToken("user_token");
            sessionManager.removeAuthToken("url");
            startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        }
        return view
    }

    private fun getAndDisplayUserInfo(view: View) {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val userToken = sessionManager.fetchAuthToken("user_token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getUserInfo(userToken)
        viewModel.userInfoResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                val userInfo: UserInfo = response.body()!!
                view.findViewById<MaterialTextView>(R.id.profile_user_first_name_value).text = userInfo.firstName
                view.findViewById<MaterialTextView>(R.id.profile_user_last_name_value).text = userInfo.lastName
                view.findViewById<MaterialTextView>(R.id.profile_user_email_value).text = userInfo.email
            }
        })
    }
}