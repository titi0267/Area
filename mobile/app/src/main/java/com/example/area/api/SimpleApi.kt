package com.example.area.api

import com.example.area.model.AREAFields
import com.example.area.model.LoginFields
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.model.about.About
import com.example.area.model.*
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {
    @POST("users")
    suspend fun register(@Body post: RegisterFields): Response<Token>

    @POST("users/login")
    suspend fun login(@Body post: LoginFields): Response<Token>

    @GET("users/me")
    suspend fun getUserInfo(@Header("Authorization") auth: String) : Response<UserInfo>

    @GET("users/areas")
    suspend fun getUserAreaList(@Header("Authorization") auth: String): Response<List<ActionReaction>>

    @POST("areas")
    suspend fun createArea(@Header("Authorization") auth: String, @Body post: AREAFields): Response<Token>

    @GET("about.json")
    suspend fun getAboutJson(): Response<About>

    @GET("/oauth/{service}/link")
    suspend fun getServiceLink(@Header("Authorization") auth: String, @Path("service") service: String) : Response<String>

    @POST("/oauth/{service}")
    suspend fun postServiceCode(@Header("Authorization") auth: String, @Path("service") service: String, @Body post: MutableMap<String, String>) : Response<Unit>

    @PUT("/areas")
    suspend fun putEnableDisable(@Header("Authorization") auth: String, @Body put: EnableDisable): Response<ActionReaction>

    @DELETE("/areas/{areaId}")
    suspend fun deleteArea(@Header("Authorization") auth: String, @Path("areaId") areaId: Int) : Response<ActionReaction>

    @GET("/oauth/google/register")
    suspend fun getGoogleRegisterLink() : Response<String>

    @POST("/oauth/google/register")
    suspend fun postRegisterWithGoogle(@Body post: OAuthCode) : Response<Token>
}