package com.example.area.utils

fun checkNames(name: String) {
    if (name.isEmpty() || name.isBlank())
        throw IllegalArgumentException("A name mustn't be empty or blank")
    if (!(name.all(Char::isLetter)))
        throw IllegalArgumentException("A name must contain only letters")
}