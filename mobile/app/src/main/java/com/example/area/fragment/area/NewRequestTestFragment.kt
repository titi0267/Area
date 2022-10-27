package com.example.area.fragment.area

import android.content.Intent
import android.os.Bundle
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
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.OAuthCode
import com.example.area.model.RegisterFields
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager

class NewRequestTestFragment : Fragment(R.layout.fragment_new_request_test) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<Button>(R.id.request_test_button).setOnClickListener {
            getOAuthLinkRequest("github")
            postServiceCode("github", OAuthCode("7021164c15c7f963e44c"))
        }
        return view
    }

    private fun getOAuthLinkRequest(service: String)
    {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getServiceLink(service)
        viewModel.linkResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, response.body()!!.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun postServiceCode(service: String, code: OAuthCode)
    {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val token = sessionManager.fetchAuthToken("token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.postServiceCode(token, service, code)
        viewModel.emptyResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, "Code successfully added", Toast.LENGTH_SHORT).show()
            }
        })
    }
}