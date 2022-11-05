package com.example.area.utils

fun checkNames(name: String) {
    if (name.isEmpty() || name.isBlank())
        throw IllegalArgumentException("Mustn't be empty")
    if (!(name.all(Char::isLetter)))
        throw IllegalArgumentException("Must contain letters")
    if (name.length > 25)
        throw IllegalArgumentException("More than 25 chars")
}