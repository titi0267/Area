package com.example.area.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.area.R
import com.example.area.utils.SessionManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Redirect to the good activity (if the user is connected or not)
        if (sessionManager.fetchAuthToken("url") == null || sessionManager.fetchAuthToken("user_token") == null) {
            startActivity(
                Intent(
                    applicationContext,
                    UserConnectionActivity::class.java
                )
            )
        }
        else {
            startActivity(
                Intent(
                    applicationContext,
                    AreaActivity::class.java
                )
            )
        }
    }
}