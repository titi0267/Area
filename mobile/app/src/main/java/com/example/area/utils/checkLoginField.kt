package com.example.area.utils

import com.example.area.model.LoginFields

fun checkLoginField(loginFields: LoginFields): Boolean {
    return (checkEmail(loginFields.email) && checkPassword(loginFields.password))
}