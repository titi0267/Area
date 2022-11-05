package com.example.area

import android.app.Application
import android.graphics.Bitmap
import com.example.area.model.UserInfo
import com.example.area.model.about.About
import com.example.area.model.about.AboutClass
import com.example.area.utils.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AREAApplication : Application() {
    var aboutClass: AboutClass? = null
    var aboutBitmapList: List<Bitmap>? = null
    var userInfo: UserInfo? = null

    fun setAboutClass(about: About) {
        aboutClass = AboutClass(about)
    }

    fun setAboutBitmapList() {
        val sessionManager = SessionManager(this)
        val url = sessionManager.fetchAuthToken("url") ?: return
        GlobalScope.launch {
            if (aboutClass != null)
                aboutBitmapList = aboutClass!!.getBitmapList(url)
        }
    }

    fun setUserInfoInApp(user: UserInfo) {
        userInfo = user
    }

    fun isAboutClassSet() : Boolean {
        aboutClass ?: return false
        return true
    }
}