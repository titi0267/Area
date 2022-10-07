package com.example.area

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AreaListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list)
        findViewById<Button>(R.id.areaCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaCreationActivity::class.java)) }
        findViewById<Button>(R.id.backFromAreaListButton).setOnClickListener { startActivity(Intent(applicationContext, MainActivity::class.java)) }
    }
}