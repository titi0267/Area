package com.example.area.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.OAuthCode
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import retrofit2.Response

class OAuthLoginWithGoogleActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        val link = intent.getStringExtra("link") ?: return

        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        finish()
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri = intent.data ?: return
        val sessionManager = SessionManager(this)

        if (!uri.toString().startsWith("http://localhost"))
            return
        val code = uri.getQueryParameter("code") ?: return
        postRegisterWithGoogle(OAuthCode(code))
    }

    private fun postRegisterWithGoogle(code: OAuthCode)
    {
        val sessionManager = SessionManager(this)
        val url = sessionManager.fetchAuthToken("url") ?: return setOAuthValue(false)
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val observer: Observer<Response<Token>?> = Observer { response ->
            if (response == null) {
                setOAuthValue(false)
                finish()
                return@Observer
            }
            if (response.isSuccessful) {
                sessionManager.saveAuthToken("user_token", response.body()!!.token)
                setOAuthValue(true)
            }
            else {
                setOAuthValue(false)
            }
            finish()
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.postRegisterWithGoogle(code, this, observer)
        viewModel.userResponse.observe(this, observer)
    }

    private fun setOAuthValue(boolean: Boolean) {
        (this.application as AREAApplication).successOauth = boolean
    }
}