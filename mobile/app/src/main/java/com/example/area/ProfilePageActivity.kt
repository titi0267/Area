package com.example.area

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.area.utils.SessionManager

class ProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViewById<Button>(R.id.backButton).setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    MainActivity::class.java
                )
            )
        }
        findViewById<Button>(R.id.profileLogoutButton).setOnClickListener {
            sessionManager.removeAuthToken("user_token");
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}