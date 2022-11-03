package com.example.area.fragment.area

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ActionReactionItemAdapter
import com.example.area.data.ActionReactionDatasource
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceInfo
import com.example.area.model.about.About
import com.example.area.model.about.AboutClass
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager
import com.example.area.utils.actionReactionInfoTranslator
import com.google.android.material.textfield.TextInputEditText
import java.net.URL

class AreaCreationActionFragment(private val actionService: ServiceInfo) : Fragment(R.layout.fragment_area_creation_action) {
    private lateinit var viewModel: MainViewModel
    private var abt: About? = null
    private var actionSelectedIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        val about = AboutJsonCreator()
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewAction)
        val actionReactionList = ActionReactionDatasource()
        var temp: ActionReactionInfo?

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        val connection = URL(actionService.imageUrl).openConnection()
        val inputStream = connection.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)

        view.findViewById<ImageView>(R.id.selectedActionServiceLogoShow).setImageDrawable(BitmapDrawable(bitmap))
        view.findViewById<TextView>(R.id.selectedActionServiceTextShow).text = actionService.name
        recycler.layoutManager = LinearLayoutManager(context as AreaActivity)
        updateRecycler(recycler, actionReactionList)

        view.findViewById<Button>(R.id.backFromActionCreationButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.nextFromActionCreation).setOnClickListener {
            if (actionSelectedIndex != -1) {
                val textHint = view.findViewById<TextInputEditText>(R.id.actionParamText).text
                if (textHint != null) {
                    if (textHint.isNotEmpty()) {
                        actionReactionList.loadActionReactionInfo()[actionSelectedIndex].paramName = textHint.toString()
                        (context as AreaActivity).changeFragment(AreaCreationReactionServiceFragment(actionService, actionReactionList.loadActionReactionInfo()[actionSelectedIndex]), "reaction_service_creation")
                    } else {
                        Toast.makeText(context as AreaActivity, "Please enter an action parameter", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context as AreaActivity, "Please select an action", Toast.LENGTH_SHORT).show()
            }
        }

        about.getAboutJson(context as AreaActivity, this, this) {
            abt = about.liveDataResponse.value
            if (abt != null) {
                actionReactionList.clear()
                for (elem in abt!!.server.services[actionService.id-1].actions) {
                    temp = actionReactionInfoTranslator(elem, null)
                    if (temp != null) {
                        actionReactionList.addService(temp!!.id, temp!!.name, temp!!.paramName, temp!!.description)
                    }
                }
                updateRecycler(recycler, actionReactionList)
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return view
    }

    private fun updateRecycler(recycler: RecyclerView, actionReactionList: ActionReactionDatasource) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ActionReactionItemAdapter(
            context as AreaActivity,
            actionReactionList.loadActionReactionInfo()
        ) { position -> onItemClick(position, actionReactionList.loadActionReactionInfo()[position].name) }
    }

    private fun onItemClick(position: Int, toPrint: String) {
        actionSelectedIndex = position
        Toast.makeText(context as AreaActivity, "$toPrint selected", Toast.LENGTH_SHORT).show()
    }
}