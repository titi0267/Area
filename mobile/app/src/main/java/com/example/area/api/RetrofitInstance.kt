package com.example.area.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(val URL_INPUT : String){

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_INPUT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}