package com.example.area.fragment.area

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
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
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ActionReactionItemAdapter
import com.example.area.data.ActionReactionDatasource
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.about.About
import com.example.area.model.about.AboutClass
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.example.area.utils.actionReactionInfoTranslator
import com.google.android.material.textfield.TextInputEditText

class AreaCreationActionFragment(private val actionService: ServiceListElement) : Fragment(R.layout.fragment_area_creation_action) {
    private var actionSelectedIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewAction)
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val actionReactionList = ActionReactionDatasource()

        view.findViewById<ImageView>(R.id.selectedActionServiceLogoShow).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, actionService.imageBitmap))
        view.findViewById<TextView>(R.id.selectedActionServiceTextShow).text = actionService.name
        recycler.layoutManager = LinearLayoutManager(context as AreaActivity)
        actionReactionList.clear()
        for (elem in aboutClass.getActionListFromServiceId(actionService.id) ?: return view) {
            actionReactionList.addService(elem.id, elem.name, elem.actionParamName, elem.description)
        }
        updateRecycler(recycler, actionReactionList)
        view.findViewById<Button>(R.id.backFromActionCreationButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.nextFromActionCreation).setOnClickListener {
            onNextClick(view, actionReactionList)
        }
        return view
    }

    private fun updateRecycler(recycler: RecyclerView, actionReactionList: ActionReactionDatasource) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ActionReactionItemAdapter(context as AreaActivity, actionReactionList.loadActionReactionInfo()) {
                position -> onItemClick(position, actionReactionList.loadActionReactionInfo()[position].name)
        }
    }

    private fun onItemClick(position: Int, toPrint: String) {
        actionSelectedIndex = position
        Toast.makeText(context as AreaActivity, "$toPrint selected", Toast.LENGTH_SHORT).show()
    }

    private fun onNextClick(view: View, actionReactionList: ActionReactionDatasource) {
        val textHint = view.findViewById<TextInputEditText>(R.id.actionParamText).text ?: return

        if (actionSelectedIndex == -1) {
            Toast.makeText(context as AreaActivity, "Please select an action", Toast.LENGTH_SHORT).show()
            return
        }
        if (textHint.isEmpty()) {
            Toast.makeText(context as AreaActivity, "Please enter an action parameter", Toast.LENGTH_SHORT).show()
            return
        }
        actionReactionList.loadActionReactionInfo()[actionSelectedIndex].paramName = textHint.toString()
        (context as AreaActivity).changeFragment(AreaCreationReactionServiceFragment(actionService, actionReactionList.loadActionReactionInfo()[actionSelectedIndex]), "reaction_service_creation")
    }
}