package com.example.area.data

import com.example.area.R
import com.example.area.model.Affirmation
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
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1),
            Affirmation(R.string.affirmation2),
            Affirmation(R.string.affirmation3),
            Affirmation(R.string.affirmation4),
            Affirmation(R.string.affirmation5),
            Affirmation(R.string.affirmation6),
            Affirmation(R.string.affirmation7),
            Affirmation(R.string.affirmation8),
            Affirmation(R.string.affirmation9),
            Affirmation(R.string.affirmation10)
        )
    }
}