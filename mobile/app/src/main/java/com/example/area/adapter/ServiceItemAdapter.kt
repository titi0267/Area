package com.example.area.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.model.ServiceInfo
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class ServiceItemAdapter(private val context: Context, private var dataset: List<ServiceInfo>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ServiceItemAdapter.ServiceViewHolder>() {
    private var selectedItem = 0

    class ServiceViewHolder(private val view: View, private val onItemClick: (position: Int) -> Unit): RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener { _ ->
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceItemAdapter.ServiceViewHolder {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        if (selectedItem == itemCount)
            selectedItem = 0
        val connection = URL(dataset[selectedItem].imageUrl).openConnection()
        val inputStream = connection.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        adapterLayout.findViewById<ImageView>(R.id.serviceItemLogo).setImageDrawable(BitmapDrawable(bitmap))
        adapterLayout.findViewById<TextView>(R.id.serviceItemName).text = dataset[selectedItem].name
        selectedItem++

        return ServiceViewHolder(adapterLayout, onItemClick)
    }

    fun filter(toSearch: String?) {
        val filterList: MutableList<ServiceInfo> = ArrayList()
        if (toSearch != null && toSearch.isNotEmpty()) {
            for (item in dataset) {
                if (item.name.lowercase(Locale.ROOT).contains(toSearch))
                    filterList += item
            }
            dataset = filterList
        }
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}