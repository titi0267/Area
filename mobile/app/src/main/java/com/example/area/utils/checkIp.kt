package com.example.area.utils

import android.util.Patterns

fun checkIp(ip: String) {
    if (ip.isEmpty() || ip.isBlank())
        throw IllegalArgumentException("IP mustn't be empty")
    if (!Patterns.IP_ADDRESS.matcher(ip).matches())
        throw IllegalArgumentException("IP format is not valid")
}