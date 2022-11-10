package com.example.area.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.OAuthCode
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import retrofit2.Response
import java.net.URLEncoder

class OAuthConnectionActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        val link = intent.getStringExtra("link") ?: return
        val service = intent.getStringExtra("service") ?: return

        sessionManager.saveAuthToken("service", service)
        Log.d("Service at creation", service)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        finish()
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri = intent.data ?: return
        val sessionManager = SessionManager(this)

        if (!uri.toString().startsWith("http://localhost"))
            return
        val queryParameterNames = uri.queryParameterNames
        val queryParams: MutableMap<String, String> = mutableMapOf()
        for (queryParameterName in queryParameterNames) {
            val queryParameterValue = uri.getQueryParameter(queryParameterName) ?: continue
            queryParams[queryParameterName] = queryParameterValue
        }
        val service = sessionManager.fetchAuthToken("service") ?: return
        sessionManager.removeAuthToken("service")
        postServiceCode(service, queryParams)
    }

    private fun postServiceCode(service: String, code: MutableMap<String, String>)
    {
        val sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken("user_token") ?: return setOAuthValue(false)
        val url = sessionManager.fetchAuthToken("url") ?: return setOAuthValue(false)
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val observer: Observer<Response<Unit>?> = Observer { response ->
            if (response == null) {
                finish()
                return@Observer
            }
            if (response.isSuccessful) {
                (this.application as AREAApplication).setTokenInTokenTable(service, code["code"])
                Toast.makeText(this, "Code successfully added", Toast.LENGTH_SHORT).show()
                setOAuthValue(true)
            } else {
                setOAuthValue(false)
            }
            finish()
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.postServiceCode(token, service, code, this, observer)
        viewModel.emptyResponse.observe(this, observer)
    }

    private fun setOAuthValue(boolean: Boolean) {
        (this.application as AREAApplication).successOauth = boolean
    }
}