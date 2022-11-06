package com.example.area.model

import android.graphics.Bitmap

data class AreaListElement(
    val actionServiceBitmap: Bitmap,
    val reactionServiceBitmap: Bitmap,
    val actionName: String,
    val reactionName: String
)
