package com.example.area.utils

import android.util.Patterns
import java.util.regex.Pattern

fun urlParser(ip: String, port: String): String {
    checkIp(ip)
    checkPort(port)
    return ("http://$ip:$port/")
}