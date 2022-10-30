package com.example.area.fragment.area

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.hardware.Camera.Area
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.MainActivity
import com.example.area.activity.OAuthConnectionActivity
import com.example.area.activity.UserConnectionActivity
import com.example.area.adapter.ItemAdapter
import com.example.area.model.OAuthCode
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.oauth_github_button).setOnClickListener {
            getOAuthLinkRequest("spotify")
        }
        view.findViewById<Button>(R.id.profileLogoutButton).setOnClickListener {
            sessionManager.removeAuthToken("user_token");
            sessionManager.removeAuthToken("url");
            startActivity(Intent(context, MainActivity::class.java))
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
                val oAuthLink = response.body()!!.toString()
                val bundle = Bundle()
                val intent = Intent(context as AreaActivity, OAuthConnectionActivity::class.java)
                bundle.putString("link", oAuthLink)
                intent.putExtras(bundle)
                startActivity(intent)
                val code = sessionManager.fetchAuthToken("code") ?: return@Observer
                sessionManager.removeAuthToken("code")
                postServiceCode(service, OAuthCode(code))
            }
        })
    }

    private fun postServiceCode(service: String, code: OAuthCode)
    {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val token = sessionManager.fetchAuthToken("user_token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.postServiceCode(token, service, code)
        viewModel.emptyResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response", response.code().toString())
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, "Code successfully added", Toast.LENGTH_SHORT).show()
            }
        })
    }

}