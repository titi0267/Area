package com.example.area.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.model.AreaInfo

class ItemAdapter(private val context: Context, private val dataset: List<AreaInfo>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var selectedItem = 0
    class ItemViewHolder(private val view: View, private val onItemClick: (position: Int) -> Unit): RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener { _ ->
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        if (selectedItem == itemCount)
            selectedItem = 0
        adapterLayout.findViewById<TextView>(R.id.actionItemText).text = dataset[selectedItem].actionStr
        adapterLayout.findViewById<TextView>(R.id.reactionItemText).text = dataset[selectedItem].reactionStr
        adapterLayout.findViewById<ImageView>(R.id.leftLogo).setImageResource((dataset[selectedItem].actionService))
        adapterLayout.findViewById<ImageView>(R.id.rightLogo).setImageResource((dataset[selectedItem].reactionService))
        selectedItem++

        return ItemViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}