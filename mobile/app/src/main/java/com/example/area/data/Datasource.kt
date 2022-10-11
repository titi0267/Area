package com.example.area.data

import com.example.area.R
import com.example.area.model.AreaInfo

class Datasource {
    private var areaList:List<AreaInfo> = ArrayList()
    fun addArea(actionId: Int, reactionId: Int, action: String, reaction: String) {
        areaList += (AreaInfo(getItemLogo(actionId), action, getItemLogo(reactionId), reaction))
    }
    private fun getItemLogo(logoToFind: Int): Int {
        return (when (logoToFind) {
            0 -> R.drawable.free_youtube_logo_icon_2431_thumb //Youtube
            1 -> R.drawable._968823 //Twitter
            2 -> R.drawable.discord_logo_4_1 //Discord
            3 -> R.drawable._5231 //Github
            4 -> R.drawable._8482beecef1014c0b5e4a36 //Trello
            5 -> R.drawable.spotify_logo_vector //Spotify
            else -> 0
        })
    }
    fun loadAreaInfo(): List<AreaInfo> {
        return areaList
    }
}