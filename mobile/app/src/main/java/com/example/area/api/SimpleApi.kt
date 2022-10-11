package com.example.area.api

import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.model.about.About
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SimpleApi {
    @POST("users")
    suspend fun register(@Body post: RegisterFields): Response<Token>

    @POST("users/login")
    suspend fun login(@Body post: LoginFields): Response<Token>

    @POST("areas")
    suspend fun createArea(@Body post: AREAFields): Response<Token>

    @GET("about.json")
    suspend fun getAboutJson(): Response<About>
}