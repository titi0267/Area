package com.example.area.api

import com.example.area.model.RegisterFields
import com.example.area.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {
    @POST("users")
    suspend fun register(@Body post: RegisterFields): Response<Token>
}