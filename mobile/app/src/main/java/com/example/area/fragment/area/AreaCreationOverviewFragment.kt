package com.example.area.fragment.area

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.about.AboutClass

class AreaCreationOverviewFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo, private val reactionService: ServiceListElement, private val reaction: ActionReactionInfo) : Fragment(R.layout.fragment_area_creation_overview) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view

        view = setActionPart(actionService, action, aboutClass, view)
        view = setReactionPart(reactionService, reaction, aboutClass, view)
        view.findViewById<Button>(R.id.areaCreationButtonOverview).setOnClickListener {
            createArea()
        }
        return view
    }

    private fun createArea() {

    }

    private fun setActionPart(actionService: ServiceListElement, action: ActionReactionInfo, aboutClass: AboutClass, view: View) : View {
        val newConstraintLayout = view.findViewById<ConstraintLayout>(R.id.actionOverviewWithoutLayout) ?: return view
        val newConstraintLayoutTwo = view.findViewById<ConstraintLayout>(R.id.actionOverviewWithLayout) ?: return view
        if (aboutClass.getServiceActionParamNameById(actionService.id, action.id)?.isEmpty() == true) {
            newConstraintLayout.findViewById<TextView>(R.id.actionReactionNameOverview).text = "Action"
            newConstraintLayout.findViewById<ImageView>(R.id.actionLogoOverview).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, actionService.imageBitmap))
            newConstraintLayout.findViewById<TextView>(R.id.actionNameOverview).text = aboutClass.getServiceActionNameById(actionService.id, action.id)
            newConstraintLayout.findViewById<TextView>(R.id.actionDescriptionOverview).text = aboutClass.getServiceActionDescriptionById(actionService.id, action.id)
            view.findViewById<ConstraintLayout>(R.id.actionPart).updateViewLayout(newConstraintLayout, newConstraintLayout.layoutParams)
        } else {
            newConstraintLayoutTwo.findViewById<TextView>(R.id.actionReactionNameOverview).text = "Action"
            newConstraintLayoutTwo.findViewById<ImageView>(R.id.actionLogoOverview).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, actionService.imageBitmap))
            newConstraintLayoutTwo.findViewById<TextView>(R.id.actionNameOverview).text = aboutClass.getServiceActionNameById(actionService.id, action.id)
            newConstraintLayoutTwo.findViewById<TextView>(R.id.actionDescriptionOverview).text = aboutClass.getServiceActionDescriptionById(actionService.id, action.id)
            newConstraintLayoutTwo.findViewById<TextView>(R.id.actionParamOverview).text = aboutClass.getServiceActionParamNameById(actionService.id, action.id)
            newConstraintLayoutTwo.findViewById<TextView>(R.id.actionParamContentOverview).text = action.paramName
            view.findViewById<ConstraintLayout>(R.id.actionPart).updateViewLayout(newConstraintLayoutTwo, newConstraintLayoutTwo.layoutParams)
        }
        return view
    }

    private fun setReactionPart(reactionService: ServiceListElement, reaction: ActionReactionInfo, aboutClass: AboutClass, view: View) : View {
        return view
    }
}