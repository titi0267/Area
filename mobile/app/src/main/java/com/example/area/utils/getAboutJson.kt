package com.example.area.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.area.MainActivity
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.about.About
import com.example.area.repository.Repository
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import okhttp3.internal.wait

fun getAboutJson(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    viewModelStoreOwner: ViewModelStoreOwner,
): About? {
    lateinit var viewModel: MainViewModel
    val sessionManager = SessionManager(context)
    var ret: About? = null;

    val rep = Repository(sessionManager.fetchAuthToken("url")!!)
    val viewModelFactory = MainViewModelFactory(rep)
    viewModel =
        ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MainViewModel::class.java)
    viewModel.getAboutJson()
    viewModel.aboutResponse.observe(lifecycleOwner, Observer { response ->
        if (response.isSuccessful()) {
            ret = response.body()!!.copy()
        }
    }).wait()
    return (ret)
}