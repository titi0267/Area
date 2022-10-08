package com.example.area.utils

import android.text.TextUtils

fun checkEmail(email: String): Boolean {
    return (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
}