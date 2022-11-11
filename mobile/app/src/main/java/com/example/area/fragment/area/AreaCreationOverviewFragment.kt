package com.example.area.fragment.area

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.fragment.overview.OverviewItemFragment
import com.example.area.fragment.overview.OverviewItemWithoutParamFragment
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
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null


        Log.d("actionService", actionService.toString())
        Log.d("action", action.toString())
        Log.d("reactionService", reactionService.toString())
        Log.d("reaction", reaction.toString())
        setOverviewFragment(R.id.overview_action_fragment_container, overviewFragmentPicker("Action", actionService, action), "overview_action_fragment")
        setOverviewFragment(R.id.overview_reaction_fragment_container, overviewFragmentPicker("Reaction", reactionService, reaction), "overview_reaction_fragment")
        view.findViewById<Button>(R.id.overview_create_button).setOnClickListener {
            createArea()
        }
        return view
    }

    private fun createArea() {

    }

    private fun overviewFragmentPicker(type: String, service: ServiceListElement, actionReactionInfo: ActionReactionInfo) : Fragment {
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return Fragment()
        val paramName: String = if (type == "Action") { aboutClass.getServiceActionParamNameById(service.id, actionReactionInfo.id) ?: "" } else { aboutClass.getServiceReactionParamNameById(service.id, actionReactionInfo.id) ?: "" }

        if (paramName.isNotEmpty()) {
            return OverviewItemFragment(type, service, actionReactionInfo)
        }
        return OverviewItemWithoutParamFragment(type, service, actionReactionInfo)
    }

    private fun setReactionPart(reactionService: ServiceListElement, reaction: ActionReactionInfo, aboutClass: AboutClass, view: View) : View {
        return view
    }

    private fun setOverviewFragment(fragmentContainer: Int, fragment: Fragment, tag: String?) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(fragmentContainer, fragment, tag)
        }
    }
}