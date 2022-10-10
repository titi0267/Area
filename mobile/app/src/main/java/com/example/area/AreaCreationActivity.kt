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
import com.example.area.data.Datasource

class AreaCreationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var actionId = 0
    var reactionId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_creation)
        val serviceList = arrayOf("Youtube", "Twitter", "Trello", "Discord", "Github", "Spotify")
        val actServSpi = findViewById<Spinner>(R.id.actionServiceSpinner)
        val reactServSpi = findViewById<Spinner>(R.id.reactionServiceSpinner)
        findViewById<Button>(R.id.backFromCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
        actServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
        reactServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
        findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
            // Creates the area
            Datasource().addArea(actionId, reactionId, "Like a video", "Post a tweet")
        }
        actServSpi.onItemSelectedListener = this@AreaCreationActivity
        reactServSpi.onItemSelectedListener = this@AreaCreationActivity
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val youtubeActions = arrayOf("New subscriber", "New Like", "New suggestion")
        val youtubeReactions = arrayOf("Post a Short", "Post a Video", "Like a Video")
        val twitterActions = arrayOf("New Follower", "New Retweet", "New Like")
        val twitterReactions = arrayOf("Tweet", "Remove Account", "MP Someone")
        val actionSpinner = findViewById<Spinner>(R.id.actionServiceSpinner)
        val reactionSpinner = findViewById<Spinner>(R.id.reactionServiceSpinner)
        actionId = actionSpinner.selectedItemPosition
        reactionId = reactionSpinner.selectedItemPosition
        findViewById<Spinner>(R.id.actionSpinner).adapter = when (actionSpinner.selectedItemPosition) {
            0 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeActions)
            1 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, twitterActions)
            else -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeActions)
        }
        findViewById<Spinner>(R.id.reactionSpinner).adapter = when (reactionSpinner.selectedItemPosition) {
            0 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeReactions)
            1 -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, twitterReactions)
            else -> ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, youtubeReactions)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}