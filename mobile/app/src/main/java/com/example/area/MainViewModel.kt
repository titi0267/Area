package com.example.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class MainViewModel(private val repository: Repository):ViewModel() {

    val emptyResponse: MutableLiveData<Response<Unit>> = MutableLiveData()
    val userInfoResponse: MutableLiveData<Response<UserInfo>> = MutableLiveData()
    val linkResponse: MutableLiveData<Response<String>> = MutableLiveData()
    val userResponse: MutableLiveData<Response<Token>> = MutableLiveData()
    val aboutResponse: MutableLiveData<Response<About>> = MutableLiveData()
    val userResponse2: MutableLiveData<Response<List<ActionReaction>>> = MutableLiveData()
    val enableResponse: MutableLiveData<Response<ActionReaction>> = MutableLiveData()
    val deleteAreaResponse: MutableLiveData<Response<ActionReaction>> = MutableLiveData()

    // Back calls
    fun register(registerFields: RegisterFields) {
        viewModelScope.launch {
            val response = repository.register(registerFields)
            userResponse.value = response
        }
    }
    fun login(loginFields: LoginFields) {
        viewModelScope.launch {
            val response = repository.login(loginFields)
            userResponse.value = response
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
    fun areaCreation(auth:String, areaFields: AREAFields) {
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
    fun getServiceLink(service: String) {
        viewModelScope.launch {
            val response = repository.getServiceLink(service)
            linkResponse.value = response
        }
    }
    fun postServiceCode(auth: String, service: String, code: OAuthCode) {
        viewModelScope.launch {
            val response = repository.postServiceCode(auth, service, code)
            emptyResponse.value = response
        }
    }
    fun putEnableDisable(auth: String, enable: EnableDisable) {
        viewModelScope.launch {
            val response = repository.putEnableDisable(auth, enable)
            enableResponse.value = response
        }
    }
    fun deleteArea(auth: String, areaId: Int) {
        viewModelScope.launch {
            val response = repository.deleteArea(auth, areaId)
            deleteAreaResponse.value = response
        }
    }
}