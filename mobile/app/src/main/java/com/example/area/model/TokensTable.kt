package com.example.area.model

data class TokensTable(
    val id: Int,
    val userId: Int,
    val twitterToken: String,
    val githubToken: String,
    val googleToken: String,
    val trelloToken: String,
    val spotifyToken: String
)