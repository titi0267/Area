package com.example.area

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AreaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_main)
        findViewById<FloatingActionButton>(R.id.profileButton).setOnClickListener { startActivity(
            Intent(applicationContext, ProfilePageActivity::class.java)
        ) }
        findViewById<Button>(R.id.areaListButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}