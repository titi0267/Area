package com.example.area

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.area.model.*
import com.example.area.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository):ViewModel() {

    val userResponse: MutableLiveData<Response<Token>> = MutableLiveData()
    val userResponse2: MutableLiveData<Response<List<ActionReaction>>> = MutableLiveData()

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
    fun getUserAreaList(auth: String) {
        viewModelScope.launch {
            val response = repository.getUserAreaList(auth)
            userResponse2.value = response
        }
    }
    fun areaCreation(areaFields: AREAFields) {
        viewModelScope.launch {
            val response = repository.areaCreation(areaFields)
            userResponse.value = response
        }
    }
}