package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReaction
import com.example.area.model.about.About
import com.example.area.utils.AboutJsonCreator

class AreaListItemFragment(private val item: ActionReaction) : Fragment(R.layout.fragment_area_list_item) {

    private var abt: About? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val about = AboutJsonCreator()

        view.findViewById<Button>(R.id.backFromAreaListItemButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        about.getAboutJson(context as AreaActivity, this, this) {
            abt = about.liveDataResponse.value
            if (abt != null) {
                view.findViewById<TextView>(R.id.actionServiceTextInItem).text = abt!!.server.services[item.actionServiceId-1].name
                view.findViewById<TextView>(R.id.actionNameInItem).text = abt!!.server.services[item.actionServiceId-1].actions[item.actionId-1].name
                view.findViewById<TextView>(R.id.actionParamInItem).text = item.actionParam
                view.findViewById<TextView>(R.id.reactionServiceTextInItem).text = abt!!.server.services[item.reactionServiceId-1].name
                view.findViewById<TextView>(R.id.reactionNameInItem).text = abt!!.server.services[item.reactionServiceId-1].reactions[item.reactionId-1].name
                view.findViewById<TextView>(R.id.reactionParamInItem).text = item.reactionParam
            }
        }

        return view
    }
}