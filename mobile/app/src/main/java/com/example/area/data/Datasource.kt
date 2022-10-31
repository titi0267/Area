package com.example.area.data

import com.example.area.R
import com.example.area.model.AreaInfo

class Datasource {
    private var areaList:MutableList<AreaInfo> = ArrayList()
    fun addArea(actionUrl: String, reactionUrl: String, action: String, reaction: String) {
        areaList += (AreaInfo(actionUrl, action, reactionUrl, reaction))
    }

    fun clear() {
        areaList.clear()
    }
    fun loadAreaInfo(): MutableList<AreaInfo> {
        return areaList
    }
}