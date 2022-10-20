package com.example.area.utils

import android.util.Log

fun checkPassword(password: String) {
    if (password.isEmpty() || password.isBlank())
        throw IllegalArgumentException("Password mustn't be empty or blank")
    if (password.length < 8)
        throw IllegalArgumentException("Password must be at least 8 characters long")
    if (password.count(Char::isDigit) == 0)
        throw IllegalArgumentException("Password must contain at least one digit")
    if (!(password.any(Char::isUpperCase)))
        throw IllegalArgumentException("Password must contain at least one uppercase character")
    if (!(password.any(Char::isLowerCase)))
        throw IllegalArgumentException("Password must contain at least one lowercase character")
    if (password.all(Char::isLetterOrDigit))
        throw IllegalArgumentException("Password must contain at least one special character")
}