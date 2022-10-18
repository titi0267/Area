package com.example.area.utils

import android.text.TextUtils

fun checkEmail(email: String) {
    if (email.isEmpty() || email.isBlank())
        throw IllegalArgumentException("Email mustn't be empty or blank")
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        throw IllegalArgumentException("Please provide a correct email")
}