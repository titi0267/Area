package com.example.area

import android.app.Application
import android.graphics.Bitmap
import com.example.area.model.about.About
import com.example.area.model.about.AboutClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AREAApplication : Application() {
    var aboutClass: AboutClass? = null
    var aboutBitmapList: List<Bitmap>? = null

    fun setAboutClass(about: About) {
        aboutClass = AboutClass(about)
    }

    fun setAboutBitmapList() {
        GlobalScope.launch {
            if (aboutClass != null)
                aboutBitmapList = aboutClass!!.getBitmapList()
        }
    }

    fun isAboutClassSet() : Boolean {
        aboutClass ?: return false
        return true
    }
}