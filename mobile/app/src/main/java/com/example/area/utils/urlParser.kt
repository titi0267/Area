package com.example.area.utils

import android.util.Patterns
import java.util.regex.Pattern

fun urlParser(ip: String, port: String): String {
    if (ip.isEmpty() || ip.isBlank())
        throw IllegalArgumentException("IP mustn't be empty or blank")
    if (!Patterns.IP_ADDRESS.matcher(ip).matches())
        throw IllegalArgumentException("IP format is not valid")
    if (port.isEmpty() || port.isBlank())
        throw IllegalArgumentException("Port mustn't be empty or blank")
    if (port.toInt() < 0 || port.toInt() > 65535)
        throw IllegalArgumentException("Port must be a number between 0 and 65535")
    return ("http://$ip:$port/")
}