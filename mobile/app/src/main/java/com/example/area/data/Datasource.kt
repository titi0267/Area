package com.example.area.data

import android.graphics.Bitmap
import com.example.area.model.AreaListElement

class Datasource {
    private var areaList: MutableList<AreaListElement> = mutableListOf<AreaListElement>()

    fun addArea(actionServiceBitmap: Bitmap, reactionServiceBitmap: Bitmap, actionName: String, reactionName: String) {
        areaList += (AreaListElement(actionServiceBitmap, reactionServiceBitmap, actionName, reactionName))
    }

    fun clear() {
        areaList.clear()
    }

    fun getAreaInfo(): MutableList<AreaListElement> {
        return areaList
    }
}