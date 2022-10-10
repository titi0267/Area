package com.example.area.data

import com.example.area.R
import com.example.area.model.AreaInfo

class Datasource {
    fun loadAreaInfo(): List<AreaInfo> {
        // Get action/reactions from back
        // Parse to set services images, action id and reaction id
        // Concat with the existing list
        return listOf<AreaInfo>(
            AreaInfo(R.drawable._5231, 0, R.drawable._968823, 0)
        )
    }
}