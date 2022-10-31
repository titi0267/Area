package com.example.area.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.model.AreaInfo
import java.net.URL

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
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        val connection = URL(dataset[selectedItem].actionService).openConnection()
        val inputStream = connection.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val connection2 = URL(dataset[selectedItem].reactionService).openConnection()
        val inputStream2 = connection2.getInputStream()
        val bitmap2 = BitmapFactory.decodeStream(inputStream2)
        adapterLayout.findViewById<TextView>(R.id.actionItemText).text = dataset[selectedItem].actionStr
        adapterLayout.findViewById<TextView>(R.id.reactionItemText).text = dataset[selectedItem].reactionStr
        adapterLayout.findViewById<ImageView>(R.id.leftLogo).setImageDrawable(BitmapDrawable(bitmap))
        adapterLayout.findViewById<ImageView>(R.id.rightLogo).setImageDrawable(BitmapDrawable(bitmap2))
        selectedItem++

        return ItemViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}