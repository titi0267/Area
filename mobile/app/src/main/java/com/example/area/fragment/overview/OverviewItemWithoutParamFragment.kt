package com.example.area.fragment.overview

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement

class OverviewItemWithoutParamFragment(val type: String, val service: ServiceListElement, val actionReactionInfo: ActionReactionInfo) : Fragment(R.layout.fragment_overview_item_without_param) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val drawable: GradientDrawable = (((AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round_service_background) ?: return view).constantState ?: return view).newDrawable().mutate()) as GradientDrawable
        drawable.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(service.id)), Color.parseColor(aboutClass.getServiceBackgroundColor(service.id)))
        view.background = drawable
        view.findViewById<TextView>(R.id.overview_item_type).text = type
        view.findViewById<ImageView>(R.id.overview_item_logo).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, service.imageBitmap))
        view.findViewById<TextView>(R.id.overview_item_name).text = actionReactionInfo.name
        view.findViewById<TextView>(R.id.overview_item_description).text = actionReactionInfo.description
        return view
    }
}