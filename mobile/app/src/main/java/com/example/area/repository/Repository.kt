package com.example.area.repository

import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.api.RetrofitInstance
import retrofit2.Response

class Repository(val URL_INPUT : String) {
    suspend fun register(registerFields: RegisterFields) : Response<Token> {
        return RetrofitInstance(URL_INPUT).api.register(registerFields)
    }
}