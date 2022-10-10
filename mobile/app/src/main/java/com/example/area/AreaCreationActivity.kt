package com.example.area

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
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.area.model.AREAFields
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager

class AreaCreationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        val sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_creation)
        val serviceList = arrayOf("Youtube", "Twitter", "Trello", "Discord", "Github", "Spotify")
        val actServSpi = findViewById<Spinner>(R.id.actionServiceSpinner)
        val reactServSpi = findViewById<Spinner>(R.id.reactionServiceSpinner)
        findViewById<Button>(R.id.backFromCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
        actServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
        reactServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
            viewModel.areaCreation(AREAFields(1, 1, 2, "dkOeRgstMCQ", 3, 1, "Nouveau like!"))
            viewModel.userResponse.observe(this, Observer { response ->
                if (response.isSuccessful()) {
                    Toast.makeText(this, "Area added successfully!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "response.body().toString()", Toast.LENGTH_SHORT).show()
                }
            })
        }
        actServSpi.onItemSelectedListener = this@AreaCreationActivity
        reactServSpi.onItemSelectedListener = this@AreaCreationActivity
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val youtubeActions = arrayOf("New subscriber", "New Like", "New suggestion")
        val youtubeReactions = arrayOf("Post a Short", "Post a Video", "Like a Video")
        val twitterActions = arrayOf("New Follower", "New Retweet", "New Like")
        val twitterReactions = arrayOf("Tweet", "Remove Account", "MP Someone")
        findViewById<Spinner>(R.id.actionSpinner).adapter = when (findViewById<Spinner>(R.id.actionServiceSpinner).selectedItemPosition) {
            0 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeActions)
            1 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, twitterActions)
            else -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeActions)
        }
        findViewById<Spinner>(R.id.reactionSpinner).adapter = when (findViewById<Spinner>(R.id.reactionServiceSpinner).selectedItemPosition) {
            0 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeReactions)
            1 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, twitterReactions)
            else -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeReactions)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}