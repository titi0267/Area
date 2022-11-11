package com.example.area.repository

import com.example.area.api.RetrofitInstance
import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.about.About
import com.example.area.model.*
import retrofit2.Response
import java.net.URL

class Repository(private val URL_INPUT : String) {
    suspend fun register(registerFields: RegisterFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.register(registerFields)
    }
    suspend fun login(loginFields: LoginFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.login(loginFields)
    }
    suspend fun getUserInfo(auth: String): Response<UserInfo> {
        return RetrofitInstance(URL_INPUT).api.getUserInfo(auth)
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
    suspend fun getServiceLink(auth: String, service: String) : Response<String>{
        return RetrofitInstance(URL_INPUT).api.getServiceLink(auth, service)
    }
    suspend fun postServiceCode(auth: String, service: String, code: MutableMap<String, String>) : Response<Unit> {
        return RetrofitInstance(URL_INPUT).api.postServiceCode(auth, service, code)
    }
    suspend fun putEnableDisable(auth: String, enable: EnableDisable) : Response<ActionReaction> {
        return RetrofitInstance(URL_INPUT).api.putEnableDisable(auth, enable)
    }
    suspend fun deleteArea(auth: String, areaId: Int) : Response<ActionReaction> {
        return RetrofitInstance(URL_INPUT).api.deleteArea(auth, areaId)
    }
    suspend fun getGoogleRegisterLink() : Response<String> {
        return RetrofitInstance(URL_INPUT).api.getGoogleRegisterLink()
    }
    suspend fun postRegisterWithGoogle(code: OAuthCode) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.postRegisterWithGoogle(code)
    }
    suspend fun deleteTokens(auth: String, tokensTable: MutableMap<String, Boolean>) : Response<Unit> {
        return RetrofitInstance(URL_INPUT).api.deleteTokens(auth, tokensTable)
    }

}