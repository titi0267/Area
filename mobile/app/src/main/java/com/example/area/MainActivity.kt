package com.example.area

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.area.utils.SessionManager
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (sessionManager.fetchAuthToken("user_token") == null)
            startActivity(Intent(applicationContext, NotLoggedInActivity::class.java))
        else {
            startActivity(Intent(applicationContext, AreaMainActivity::class.java))
        }
    }
}