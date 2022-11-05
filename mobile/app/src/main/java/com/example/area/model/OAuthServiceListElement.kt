package com.example.area.model

import android.graphics.Bitmap

data class OAuthServiceListElement(
    val id: Int,
    val name: String,
    val oauthName: String,
    val imageBitmap: Bitmap,
    val connected: Boolean
)
