package com.example.area

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.area.adapter.ItemAdapter
import com.example.area.data.Datasource
import javax.sql.DataSource

class AreaListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list)
        // Button management
        findViewById<Button>(R.id.areaCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaCreationActivity::class.java)) }
        findViewById<Button>(R.id.backFromAreaListButton).setOnClickListener { startActivity(Intent(applicationContext, MainActivity::class.java)) }

        // Scrolling management
        val myDataSet = Datasource().loadAffirmations()
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter = ItemAdapter(this, myDataSet)
        recycler.setHasFixedSize(true)
    }
}