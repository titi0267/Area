package com.example.area.adapter

import android.content.Context
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.model.ActionReactionInfo

class ActionReactionItemAdapter(private val context: Context, private val dataset: List<ActionReactionInfo>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ActionReactionItemAdapter.ActionReactionViewHolder>() {
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
        if (selectedItem == itemCount)
            selectedItem = 0
        adapterLayout.findViewById<TextView>(R.id.actionReactionItemName).text = dataset[selectedItem].name
        selectedItem++

        return ActionReactionViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ActionReactionViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}