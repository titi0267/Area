package com.example.area.utils

import com.example.area.model.RegisterFields

fun checkRegisterField(registerFields: RegisterFields): Boolean {
    return (checkEmail(registerFields.email) && checkPassword(registerFields.password) &&
            checkNames(registerFields.firstName) && checkNames(registerFields.lastName))
}