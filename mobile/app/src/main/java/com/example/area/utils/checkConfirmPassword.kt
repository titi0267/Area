package com.example.area.utils

fun checkConfirmPassword(password: String, confirmPassword: String) {
    if (password != confirmPassword)
        throw IllegalArgumentException("Confirm must be the same as Password")
}