package com.example.area.utils

fun checkPort(port: String) {
    if (port.isEmpty() || port.isBlank())
        throw IllegalArgumentException("Port mustn't be empty")
    if (port.toInt() < 0 || port.toInt() > 65535)
        throw IllegalArgumentException("Port must be 0-65535")
}