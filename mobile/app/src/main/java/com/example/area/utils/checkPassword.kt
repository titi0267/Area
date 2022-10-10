package com.example.area.utils

fun checkPassword(password: String): Boolean {
    return (password.isNotEmpty() && password.isNotBlank() && password.length > 6)
}