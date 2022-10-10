package com.example.area.utils

fun checkNames(firstName: String): Boolean {
    return firstName.isNotEmpty() && firstName.isNotBlank() && (firstName.length < 25)
}