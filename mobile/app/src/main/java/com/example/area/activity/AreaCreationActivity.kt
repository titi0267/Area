package com.example.area.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.model.AREAFields
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager

class AreaCreationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private var abt: About? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_creation)
        val about = AboutJsonCreator()
        about.getAboutJson(this, this, this) {
            abt = about.liveDataResponse.value;
            var serviceList = arrayOf<String>()
            val actServSpi = findViewById<Spinner>(R.id.actionServiceSpinner)
            val reactServSpi = findViewById<Spinner>(R.id.reactionServiceSpinner)
            Toast.makeText(this, "Got no about", Toast.LENGTH_SHORT).show()
            if (abt != null) {
                Toast.makeText(this, "Got about", Toast.LENGTH_SHORT).show()
                for (elem in abt!!.server.services) {
                    serviceList += elem.name
                }
            }
            if (serviceList.isNotEmpty()) {
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
                actServSpi.adapter = ArrayAdapter(this,
                    R.layout.layout_spinner,
                    R.id.textSpinner, serviceList)
                reactServSpi.adapter = ArrayAdapter(this,
                    R.layout.layout_spinner,
                    R.id.textSpinner, serviceList)
                findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
                    viewModel.areaCreation(sessionManager.fetchAuthToken("user_token")!!, AREAFields(1, 1, 2, "yY1vTll0O1w", 3, 1, "Nouveau like!"))
                    viewModel.userResponse.observe(this, Observer { response ->
                        if (response.isSuccessful) {
                            Toast.makeText(this, "Area added successfully!", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                actServSpi.onItemSelectedListener = this@AreaCreationActivity
                reactServSpi.onItemSelectedListener = this@AreaCreationActivity
            }
        }
        findViewById<Button>(R.id.backFromCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var actionList = arrayOf<String>()
        var reactionList = arrayOf<String>()
        val actionSpinner = findViewById<Spinner>(R.id.actionServiceSpinner)
        val reactionSpinner = findViewById<Spinner>(R.id.reactionServiceSpinner)
        if (abt != null) {
            for (elem in abt!!.server.services[actionSpinner.selectedItemPosition].actions) {
                actionList += elem.name
            }
        }
        findViewById<Spinner>(R.id.actionSpinner).adapter = ArrayAdapter(this,
            R.layout.layout_spinner,
            R.id.textSpinner, actionList)
        if (abt != null) {
            for (elem in abt!!.server.services[reactionSpinner.selectedItemPosition].reactions) {
                reactionList += elem.name
            }
        }
        findViewById<Spinner>(R.id.reactionSpinner).adapter = ArrayAdapter(this,
            R.layout.layout_spinner,
            R.id.textSpinner, reactionList)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}