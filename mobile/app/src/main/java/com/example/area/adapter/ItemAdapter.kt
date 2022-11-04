package com.example.area.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.model.AreaListElement

class ItemAdapter(private val context: Context, private val dataset: List<AreaListElement>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
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
        adapterLayout.findViewById<TextView>(R.id.actionItemText).text = dataset[selectedItem].actionName
        adapterLayout.findViewById<TextView>(R.id.reactionItemText).text = dataset[selectedItem].reactionName
        adapterLayout.findViewById<ImageView>(R.id.leftLogo).setImageDrawable(BitmapDrawable(context.resources, dataset[selectedItem].actionServiceBitmap))
        adapterLayout.findViewById<ImageView>(R.id.rightLogo).setImageDrawable(BitmapDrawable(context.resources, dataset[selectedItem].reactionServiceBitmap))
        selectedItem++
        return ItemViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}