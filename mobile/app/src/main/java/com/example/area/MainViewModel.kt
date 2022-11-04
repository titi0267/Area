package com.example.area

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.model.about.About
import com.example.area.model.*
import com.example.area.repository.Repository
import kotlinx.coroutines.job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class MainViewModel(private val repository: Repository) : ViewModel() {

    val emptyResponse: MutableLiveData<Response<Unit>> = MutableLiveData()
    val userInfoResponse: MutableLiveData<Response<UserInfo>> = MutableLiveData()
    val linkResponse: MutableLiveData<Response<String>> = MutableLiveData()
    val userResponse: MutableLiveData<Response<Token>?> = MutableLiveData()
    val aboutResponse: MutableLiveData<Response<About>> = MutableLiveData()
    val userResponse2: MutableLiveData<Response<List<ActionReaction>>> = MutableLiveData()

    // Back calls

    fun register(registerFields: RegisterFields, context: Context) {
        viewModelScope.launch {
            (context as UserConnectionActivity).loading = true
            userResponse.value = null
            try {
                Toast.makeText(context as UserConnectionActivity, "Loading...", Toast.LENGTH_SHORT).show()
                val response = repository.register(registerFields)
                (context as UserConnectionActivity).loading = false
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context as UserConnectionActivity, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context as UserConnectionActivity, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                (context as UserConnectionActivity).loading = false
            }
        }
    }

    fun login(loginFields: LoginFields, context: Context, observer: Observer<Response<Token>?>) {
        viewModelScope.launch {
            (context as UserConnectionActivity).loading = true
            userResponse.value = null
            try {
                Toast.makeText(context as UserConnectionActivity, "Loading...", Toast.LENGTH_SHORT).show()
                val response = repository.login(loginFields)
                (context as UserConnectionActivity).loading = false
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context as UserConnectionActivity, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context as UserConnectionActivity, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                (context as UserConnectionActivity).loading = false
                userResponse.removeObserver(observer)
            }
        }
    }

    fun getUserInfo(auth: String) {
        viewModelScope.launch {
            val response = repository.getUserInfo(auth)
            userInfoResponse.value = response
        }
    }

    fun getUserAreaList(auth: String) {
        viewModelScope.launch {
            val response = repository.getUserAreaList(auth)
            userResponse2.value = response
        }
    }

    fun areaCreation(auth: String, areaFields: AREAFields) {
        viewModelScope.launch {
            val response = repository.areaCreation(auth, areaFields)
            userResponse.value = response
        }
    }

    fun getAboutJson() {
        viewModelScope.launch {
            val response = repository.getAboutJson()
            aboutResponse.value = response
        }
    }
    
    fun getServiceLink(auth: String, service: String) {
        viewModelScope.launch {
            val response = repository.getServiceLink(auth, service)
            linkResponse.value = response
        }
    }

    fun postServiceCode(auth: String, service: String, code: OAuthCode) {
        viewModelScope.launch {
            val response = repository.postServiceCode(auth, service, code)
            emptyResponse.value = response
        }
    }
}