
package com.example.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.area.adapter.ItemAdapter
import com.example.area.data.Datasource
import com.example.area.model.GetUserAreaList
import com.example.area.model.ActionReaction
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.example.area.utils.urlParser
import javax.sql.DataSource

class AreaListActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list)

        // Button management
        findViewById<Button>(R.id.areaCreationButton).setOnClickListener {
            startActivity(Intent(applicationContext, AreaCreationActivity::class.java))
        }
        findViewById<Button>(R.id.backFromAreaListButton).setOnClickListener { startActivity(Intent(applicationContext, MainActivity::class.java)) }

        // Scrolling management
        val myDataSet = Datasource()
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter = ItemAdapter(this, myDataSet.loadAreaInfo()) { position -> onItemClick(position) }
        recycler.setHasFixedSize(true)


        // Get area list (back request)
        val sessionManager = SessionManager(this)
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
        viewModel.userResponse2.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val jsonArray: List<ActionReaction> = response.body()!!
                myDataSet.clear()
                for (item in jsonArray) {
                    myDataSet.addArea(item.actionServiceId, item.reactionServiceId, "Hello", "World")
                }
                recycler.adapter = ItemAdapter(this, myDataSet.loadAreaInfo()) { position -> onItemClick(position) }
                recycler.setHasFixedSize(true)
            }
        })
        findViewById<Button>(R.id.area_list_fetch).setOnClickListener {
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
            viewModel.userResponse2.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    val jsonArray: List<ActionReaction> = response.body()!!
                    myDataSet.clear()
                    for (item in jsonArray) {
                        myDataSet.addArea(item.actionServiceId, item.reactionServiceId, "Hello", "World")
                    }
                    recycler.adapter = ItemAdapter(this, myDataSet.loadAreaInfo()) { position -> onItemClick(position) }
                    recycler.setHasFixedSize(true)
                }
            })
        }
    }
    private fun onItemClick(position: Int) {
        startActivity(Intent(applicationContext, AreaListItemActivity::class.java))
    }
}