package com.example.area.repository

import com.example.area.api.RetrofitInstance
import com.example.area.model.*
import retrofit2.Response

class Repository(private val URL_INPUT : String) {
    suspend fun register(registerFields: RegisterFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.register(registerFields)
    }
    suspend fun login(loginFields: LoginFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.login(loginFields)
    }
    suspend fun getUserAreaList(): Response<Token> {
        return RetrofitInstance(URL_INPUT).api.getUserAreaList()
    }
    suspend fun areaCreation(areaFields: AREAFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.createArea(areaFields)
    }
}