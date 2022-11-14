package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AreaCreationActionParamFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo) : Fragment(R.layout.fragment_area_creation_action_params) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val textEdit = view.findViewById<TextInputEditText>(R.id.actionParamText)

        view.findViewById<TextView>(R.id.actionParamFragmentText).text = ("Please put your "+actionService.name+" "+(aboutClass.getServiceActionParamNameById(actionService.id, action.id)?.lowercase(Locale.getDefault())))
        textEdit.hint = ("Enter your "+ (aboutClass.getServiceActionParamNameById(actionService.id, action.id))?.lowercase(Locale.getDefault()))
        view.findViewById<Button>(R.id.cancelButtonInActionParam).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.doneButtonInActionParam).setOnClickListener {
            val textHint = textEdit.text ?: null
            action.paramName = textHint.toString()
            (context as AreaActivity).changeFragment(AreaCreationReactionServiceFragment(actionService, action), "reaction_service_creation")
        }

        return view
    }
}