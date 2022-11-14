package com.example.area.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.area.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(prefName: String, token: String) {
        val editor = prefs.edit()
        editor.putString(prefName, token)
        editor.apply()
    }

    fun fetchAuthToken(prefName: String): String? {
        return (prefs.getString(prefName, null))
    }

    fun removeAuthToken(prefName: String) {
        val editor = prefs.edit()
        editor.remove(prefName)
        editor.apply()
    }

    fun replaceAuthToken(prefName: String, token: String) {
        val editor = prefs.edit()
        editor.remove(prefName)
        editor.putString(prefName, token)
        editor.apply()
    }
}