package com.example.area.utils

fun checkPort(port: String) {
    if (port.isEmpty() || port.isBlank())
        throw IllegalArgumentException("Mustn't be empty")
    if (port.toInt() < 0 || port.toInt() > 65535)
        throw IllegalArgumentException("0-65535")
}