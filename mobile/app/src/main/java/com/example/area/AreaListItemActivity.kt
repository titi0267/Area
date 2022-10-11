package com.example.area

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AreaListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list_item)
        findViewById<Button>(R.id.backFromAreaListItemButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
    }
}