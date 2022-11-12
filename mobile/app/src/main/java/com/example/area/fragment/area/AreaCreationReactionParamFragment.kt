package com.example.area.fragment.area

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
    private var first = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val textEdit = view.findViewById<TextInputEditText>(R.id.reactionParamText)

        first = true
        view.findViewById<TextView>(R.id.reactionParamFragmentText).text = ("Please put your "+reactionService.name+" "+(aboutClass.getServiceReactionParamNameById(reactionService.id, reaction.id)?.lowercase(Locale.getDefault())))
        textEdit.hint = ("Enter your "+ (aboutClass.getServiceReactionParamNameById(reactionService.id, reaction.id))?.lowercase(Locale.getDefault()))
        view.findViewById<Button>(R.id.cancelButtonInReactionParam).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.createButtonInReactionParam).setOnClickListener {
            reaction.paramName = textEdit.text?.toString() ?: ""
            (context as AreaActivity).changeFragment(AreaCreationOverviewFragment(actionService, action, reactionService, reaction), "overview_creation")
        }
        createPopupListActionParam(view, aboutClass)
        return view
    }

    private fun createPopupListActionParam(view: View, aboutClass: AboutClass) {
        val button = view.findViewById<Button>(R.id.action_param_button_text)
        val listActionParam = ListPopupWindow(context ?: return, null, com.google.android.material.R.attr.listPopupWindowStyle)
        val items = aboutClass.getServiceActionAvailableInjectParamsById(actionService.id, action.id) ?: return
        val adapter = ArrayAdapter(context ?: return, R.layout.action_param_item, items)
        val reactionParameterText: Editable = view.findViewById<TextInputEditText>(R.id.reactionParamText).text ?: return

        if (items.isEmpty()) {
            button.visibility = View.GONE
        }
        listActionParam.isModal = true
        listActionParam.setAdapter(adapter)
        listActionParam.anchorView = button
        listActionParam.setOnItemClickListener { _, _, i, _ ->
            reactionParameterText.append("%${items[i]}%")
            listActionParam.dismiss()
        }
        button.setOnClickListener { v: View? ->
            if (v == null)
                return@setOnClickListener
            listActionParam.show()
        }
    }
}