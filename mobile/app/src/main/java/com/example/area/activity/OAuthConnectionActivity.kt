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
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.OAuthCode
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.internal.wait

class OAuthConnectionActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        sessionManager.saveAuthToken("code", code)
        finish()
    }
}