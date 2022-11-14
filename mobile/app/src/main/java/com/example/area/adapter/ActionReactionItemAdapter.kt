package com.example.area.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReactionInfo

class ActionReactionItemAdapter(private val context: Context, private val dataset: List<ActionReactionInfo>, private val serviceId :Int, private val actionOrReaction: Boolean, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ActionReactionItemAdapter.ActionReactionViewHolder>() {
    private var selectedItem = 0

    class ActionReactionViewHolder(private val view: View, private val onItemClick: (position: Int) -> Unit): RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener { _ ->
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionReactionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.action_reaction_item, parent, false)
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return ActionReactionViewHolder(adapterLayout, onItemClick)
        val drawable: GradientDrawable? = AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round_service_background) as GradientDrawable?
        val newDrawable: GradientDrawable? = drawable?.constantState?.newDrawable()?.mutate() as GradientDrawable?

        if (selectedItem == itemCount)
            selectedItem = 0
        newDrawable?.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(serviceId)), Color.parseColor(aboutClass.getServiceBackgroundColor(serviceId)))
        adapterLayout.background = newDrawable
        adapterLayout.findViewById<TextView>(R.id.actionReactionItemName).text = dataset[selectedItem].name
        if (actionOrReaction)
            adapterLayout.findViewById<TextView>(R.id.actionReactionDescriptionOnItemList).text = aboutClass.getServiceActionDescriptionByName(serviceId, dataset[selectedItem].name)
        else
            adapterLayout.findViewById<TextView>(R.id.actionReactionDescriptionOnItemList).text = aboutClass.getServiceReactionDescriptionByName(serviceId, dataset[selectedItem].name)
        selectedItem++

        return ActionReactionViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ActionReactionViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}