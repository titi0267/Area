package com.example.area.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.model.about.About
import com.example.area.repository.Repository

class AboutJsonCreator() {
    private val mutableResponse = MutableLiveData<About>()
    val liveDataResponse: LiveData<About> get() = mutableResponse

    fun getAboutJson(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        viewModelStoreOwner: ViewModelStoreOwner,
        function: () -> Unit,
    ) {
        lateinit var viewModel: MainViewModel
        val sessionManager = SessionManager(context)

        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        viewModel =
            ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAboutJson()
        return viewModel.aboutResponse.observe(lifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                mutableResponse.value = response.body()!!.copy()
            }
        })
    }
}