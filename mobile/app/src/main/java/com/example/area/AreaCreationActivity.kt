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
import com.example.area.model.AREAFields
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText

class AreaCreationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private var abt: About? = null
    private var actionSpinnerValue = 0
    private var reactionSpinnerValue = 0

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
            if (abt != null) {
                for (elem in abt!!.server.services) {
                    serviceList += elem.name
                }
            }
            if (serviceList.isNotEmpty()) {
                actServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
                reactServSpi.adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, serviceList)
                findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
                    if (isCreatable()) {
                        Toast.makeText(this, findViewById<Spinner>(R.id.actionSpinner).selectedItemPosition.toString(), Toast.LENGTH_SHORT).show()
                        viewModel.areaCreation(sessionManager.fetchAuthToken(
                            "user_token")!!, AREAFields(
                            actServSpi.selectedItemPosition+1,
                            findViewById<Spinner>(R.id.actionSpinner).selectedItemPosition+1,
                            findViewById<TextInputEditText>(R.id.actionParamtextInput).text.toString(),
                            reactServSpi.selectedItemPosition+1,
                            findViewById<Spinner>(R.id.reactionSpinner).selectedItemPosition+1,
                            findViewById<TextInputEditText>(R.id.reactionParamTextInput).text.toString()))
                        viewModel.userResponse.observe(this, Observer { response ->
                            if (response.isSuccessful) {
                                Toast.makeText(this, "Area added successfully!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(applicationContext, AreaListActivity::class.java))
                            }
                        })
                    }
                    else {
                        Toast.makeText(this, "Fields are incomplete, no area has been created!", Toast.LENGTH_SHORT).show()
                    }
                }
                actServSpi.onItemSelectedListener = this@AreaCreationActivity
                reactServSpi.onItemSelectedListener = this@AreaCreationActivity
            }
        }
        findViewById<Button>(R.id.backFromCreationButton).setOnClickListener { startActivity(Intent(applicationContext, AreaListActivity::class.java)) }
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
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
        findViewById<Spinner>(R.id.actionSpinner).adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, actionList)
        if (abt != null) {
            for (elem in abt!!.server.services[reactionSpinner.selectedItemPosition].reactions) {
                reactionList += elem.name
            }
        }
        findViewById<Spinner>(R.id.reactionSpinner).adapter = ArrayAdapter(this, R.layout.layout_spinner, R.id.textSpinner, reactionList)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun isCreatable(): Boolean {
        val actionServiceSpinner = findViewById<Spinner>(R.id.actionServiceSpinner)
        val actionsSpinner = findViewById<Spinner>(R.id.actionSpinner)
        val actionTextInput = findViewById<TextInputEditText>(R.id.actionParamtextInput)
        val reactionServiceSpinner = findViewById<Spinner>(R.id.reactionServiceSpinner)
        val reactionsSpinner = findViewById<Spinner>(R.id.reactionSpinner)
        val reactionTextInput = findViewById<TextInputEditText>(R.id.reactionParamTextInput)

        if (actionServiceSpinner.selectedItem == null) return false
        if (actionsSpinner.selectedItem == null) return false
        if (actionTextInput.text?.isEmpty() == true) return false
        if (reactionServiceSpinner.selectedItem == null) return false
        if (reactionsSpinner.selectedItem == null) return false
        if (reactionTextInput.text?.isEmpty() == true) return false
        return true
    }
}