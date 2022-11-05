package com.example.area.model

data class UserInfo (
    val id: Int,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    var tokensTable: Map<String, String?>
)