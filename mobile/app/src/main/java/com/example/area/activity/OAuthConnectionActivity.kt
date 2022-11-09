package com.example.area.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.OAuthCode
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.internal.wait
import retrofit2.Response

class OAuthConnectionActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        val link = intent.getStringExtra("link") ?: return
        val service = intent.getStringExtra("service") ?: return

        sessionManager.saveAuthToken("service", service)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        finish()
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri = intent.data ?: return
        val sessionManager = SessionManager(this)

        if (!uri.toString().startsWith("http://localhost"))
            return
        val code = uri.getQueryParameter("code") ?: return setOAuthValue(false)
        val service = sessionManager.fetchAuthToken("service") ?: return setOAuthValue(false)
        sessionManager.removeAuthToken("service")
        postServiceCode(service, OAuthCode(code))
    }

    private fun postServiceCode(service: String, code: OAuthCode)
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
                (this.application as AREAApplication).setTokenInTokenTable(service, code.code)
                setOAuthValue(true)
            }
            else {
                setOAuthValue(false)
            }
            finish()
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.postServiceCode(token, service, code, this, observer)
        viewModel.emptyResponse.observe(this, observer)
    }

    private fun setOAuthValue(boolean: Boolean) {
        (this.application as AREAApplication).successOauth = boolean;
    }
}