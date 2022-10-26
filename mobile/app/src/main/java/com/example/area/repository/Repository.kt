package com.example.area.repository

import com.example.area.api.RetrofitInstance
import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.about.About
import com.example.area.model.*
import retrofit2.Response

class Repository(private val URL_INPUT : String) {
    suspend fun register(registerFields: RegisterFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.register(registerFields)
    }
    suspend fun login(loginFields: LoginFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.login(loginFields)
    }
    suspend fun getUserAreaList(auth: String): Response<List<ActionReaction>> {
        return RetrofitInstance(URL_INPUT).api.getUserAreaList(auth)
    }
    suspend fun areaCreation(auth: String, areaFields: AREAFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.createArea(auth, areaFields)
    }
    suspend fun getAboutJson() : Response<About> {
        return RetrofitInstance(URL_INPUT).api.getAboutJson()
    }
    suspend fun getServiceLink(service: String) : Response<String>{
        return RetrofitInstance(URL_INPUT).api.getServiceLink(service)
    }
}