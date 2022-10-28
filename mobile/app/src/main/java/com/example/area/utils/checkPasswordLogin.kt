package com.example.area.utils

fun checkPasswordLogin(password: String) {
    if (password.isEmpty() || password.isBlank())
        throw IllegalArgumentException("Password mustn't be empty or blank")
}