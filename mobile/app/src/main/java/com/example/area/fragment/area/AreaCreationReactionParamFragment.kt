package com.example.area.fragment.area

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.about.AboutClass
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AreaCreationReactionParamFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo, private val reactionService: ServiceListElement, private val reaction: ActionReactionInfo) : Fragment(R.layout.fragment_area_creation_reaction_params) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val textEdit = view.findViewById<TextInputEditText>(R.id.reactionParamText)

        view.findViewById<TextView>(R.id.reactionParamFragmentText).text = ("Please put your "+reactionService.name+" "+(aboutClass.getServiceReactionParamNameById(reactionService.id, reaction.id)?.lowercase(Locale.getDefault())))
        textEdit.hint = ("Enter your "+ (aboutClass.getServiceReactionParamNameById(reactionService.id, reaction.id))?.lowercase(Locale.getDefault()))
        view.findViewById<Button>(R.id.cancelButtonInReactionParam).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.createButtonInReactionParam).setOnClickListener {
            reaction.paramName = textEdit.text?.toString() ?: ""
            (context as AreaActivity).changeFragment(AreaCreationOverviewFragment(actionService, action, reactionService, reaction), "overview_creation")
        }
        createSpinner(view, aboutClass)
        return view
    }

    private fun createSpinner(view: View, aboutClass: AboutClass) {
        var first: Boolean = true
        val injectSpinner = view.findViewById<Spinner>(R.id.injectActionParamsSpinner)
        val injectedParams: ArrayAdapter<String> = ArrayAdapter(context as AreaActivity, android.R.layout.simple_spinner_item, aboutClass.getServiceActionAvailableInjectParamsById(actionService.id, action.id) ?: return)
        injectedParams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        injectSpinner.isClickable = true
        injectSpinner.adapter = injectedParams
        injectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>?, viewSpinner: View?, pos: Int, p3: Long) {
                val reactionParameterText: Editable = view.findViewById<TextInputEditText>(R.id.reactionParamText).text ?: return

                if (first) {
                    first = false
                    return
                }
                if (adapter == null)
                    return
                reactionParameterText.append("%${adapter.selectedItem}%")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}