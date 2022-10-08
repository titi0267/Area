package com.example.area

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.area.utils.SessionManager

class NotLoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_logged_in)
        findViewById<Button>(R.id.not_logged_in_login_button).setOnClickListener { /*startActivity(Intent(applicationContext, LoginActivity::class.java))*/ }
        findViewById<Button>(R.id.not_logged_in_register_button).setOnClickListener {
            startActivity(
                Intent(applicationContext, RegisterActivity::class.java)
            )
        }
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}