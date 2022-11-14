package com.example.area.utils

import com.example.area.model.RegisterFields

fun checkRegisterField(registerFields: RegisterFields) {
    checkNames(registerFields.firstName)
    checkNames(registerFields.lastName)
    checkEmail(registerFields.email)
    checkPasswordRegister(registerFields.password)
}