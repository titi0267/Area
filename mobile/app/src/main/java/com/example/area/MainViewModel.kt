package com.example.area

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.area.activity.AreaActivity
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.model.about.About
import com.example.area.model.*
import com.example.area.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class MainViewModel(private val repository: Repository) : ViewModel() {

    val emptyResponse: MutableLiveData<Response<Unit>?> = MutableLiveData()
    val userInfoResponse: MutableLiveData<Response<UserInfo>?> = MutableLiveData()
    val linkResponse: MutableLiveData<Response<String>?> = MutableLiveData()
    val userResponse: MutableLiveData<Response<Token>?> = MutableLiveData()
    val aboutResponse: MutableLiveData<Response<About>?> = MutableLiveData()
    val listAREAResponse: MutableLiveData<Response<List<ActionReaction>>?> = MutableLiveData()
    val enableResponse: MutableLiveData<Response<ActionReaction>?> = MutableLiveData()
    val deleteAreaResponse: MutableLiveData<Response<ActionReaction>?> = MutableLiveData()

    // Back calls

    fun register(registerFields: RegisterFields, context: Context, observer: Observer<Response<Token>?>) {
        viewModelScope.launch {
            (context as UserConnectionActivity).loading = true
            try {
                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                val response = repository.register(registerFields)
                (context).loading = false
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                (context).loading = false
                userResponse.removeObserver(observer)
            }
        }
    }

    fun login(loginFields: LoginFields, context: Context, observer: Observer<Response<Token>?>) {
        viewModelScope.launch {
            (context as UserConnectionActivity).loading = true
            try {
                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                val response = repository.login(loginFields)
                (context).loading = false
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                (context).loading = false
                userResponse.removeObserver(observer)
            }
        }
    }

    fun getUserInfo(auth: String, context: Context, observer: Observer<Response<UserInfo>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.getUserInfo(auth)
                (context).loading = false
                userInfoResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userInfoResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userInfoResponse.value = null
            }
            finally {
                (context).loading = false
                userInfoResponse.removeObserver(observer)
            }
        }
    }

    fun getUserAreaList(auth: String, context: Context, observer: Observer<Response<List<ActionReaction>>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.getUserAreaList(auth)
                context.loading = false
                listAREAResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                listAREAResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                listAREAResponse.value = null
            }
            finally {
                context.loading = false
                listAREAResponse.removeObserver(observer)
            }
        }
    }

    fun areaCreation(auth: String, areaFields: AREAFields, context: Context, observer: Observer<Response<Token>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.areaCreation(auth, areaFields)
                context.loading = false
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                context.loading = false
                userResponse.removeObserver(observer)
            }
        }
    }

    fun getAboutJson(context: Context, observer: Observer<Response<About>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.getAboutJson()
                (context).loading = false
                aboutResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                aboutResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                aboutResponse.value = null
            }
            finally {
                (context).loading = false
                aboutResponse.removeObserver(observer)
            }
        }
    }
    
    fun getServiceLink(auth: String, service: String, context: Context, observer: Observer<Response<String>?>) {
        viewModelScope.launch {
            linkResponse.value = null
            (context as AreaActivity).loading = true
            try {
                val response = repository.getServiceLink(auth, service)
                context.loading = false
                linkResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                linkResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                linkResponse.value = null
            }
            finally {
                (context).loading = false
                linkResponse.removeObserver(observer)
            }
        }
    }

    fun postServiceCode(auth: String, service: String, code: MutableMap<String, String>, context: Context, observer: Observer<Response<Unit>?>) {
        viewModelScope.launch {
            try {
                val response = repository.postServiceCode(auth, service, code)
                emptyResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                emptyResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                emptyResponse.value = null
            }
            finally {
                emptyResponse.removeObserver(observer)
            }
        }
    }
    fun putEnableDisable(auth: String, enable: EnableDisable, context: Context, observer: Observer<Response<ActionReaction>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.putEnableDisable(auth, enable)
                enableResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                enableResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                enableResponse.value = null
            }
            finally {
                (context).loading = false
                enableResponse.removeObserver(observer)
            }
        }
    }
    fun deleteArea(auth: String, areaId: Int, context: Context, observer: Observer<Response<ActionReaction>?>) {
        viewModelScope.launch {
            (context as AreaActivity).loading = true
            try {
                val response = repository.deleteArea(auth, areaId)
                deleteAreaResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                deleteAreaResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                deleteAreaResponse.value = null
            }
            finally {
                context.loading = false
                deleteAreaResponse.removeObserver(observer)
            }
        }
    }
    fun getGoogleRegisterLink(context: Context, observer: Observer<Response<String>?>) {
        viewModelScope.launch {
            (context as UserConnectionActivity).loading = true
            try {
                val response = repository.getGoogleRegisterLink()
                linkResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                linkResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                linkResponse.value = null
            }
            finally {
                context.loading = false
                linkResponse.removeObserver(observer)
            }
        }
    }
    fun postRegisterWithGoogle(code: OAuthCode, context: Context, observer: Observer<Response<Token>?>) {
        viewModelScope.launch {
            try {
                val response = repository.postRegisterWithGoogle(code)
                userResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                userResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                userResponse.value = null
            }
            finally {
                userResponse.removeObserver(observer)
            }
        }
    }
    fun deleteTokens(auth: String, code: MutableMap<String, Boolean>, context: Context, observer: Observer<Response<Unit>?>) {
        viewModelScope.launch {
            emptyResponse.value = null
            try {
                val response = repository.deleteTokens(auth, code)
                emptyResponse.value = response
            }
            catch(e: SocketTimeoutException) {
                Toast.makeText(context, "Error: Connection Timed Out\nThe cause might be a wrong IP/Port", Toast.LENGTH_LONG).show()
                emptyResponse.value = null
            }
            catch(e: ConnectException) {
                Toast.makeText(context, "Error: Failed to connect\nThe cause might be a wrong IP/Port", Toast.LENGTH_SHORT).show()
                emptyResponse.value = null
            }
            finally {
                emptyResponse.removeObserver(observer)
            }
        }
    }
}