package com.example.area.utils

import android.util.Patterns
import java.util.regex.Pattern

fun urlParser(ip: String, port: String): String {
    if (ip.isEmpty() || ip.isBlank() || port.isEmpty() || port.isBlank())
        return ("error")
    if (!Patterns.IP_ADDRESS.matcher(ip).matches())
        return ("error")
    val portNumber = port.toIntOrNull()
    portNumber?.let {
        if (portNumber < 0 || portNumber > 65535)
            return ("error")
    } ?:
        return ("error")
    return ("http://$ip:$port/")
}