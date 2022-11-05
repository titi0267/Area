package com.example.area.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ServiceListElement
import java.util.*
import kotlin.collections.ArrayList

class ServiceItemAdapter(private val context: Context, private var dataset: List<ServiceListElement>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ServiceItemAdapter.ServiceViewHolder>() {
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
        adapterLayout.findViewById<ImageView>(R.id.serviceItemLogo).setImageDrawable(BitmapDrawable(context.resources, dataset[selectedItem].imageBitmap))
        adapterLayout.findViewById<TextView>(R.id.serviceItemName).text = dataset[selectedItem].name
        selectedItem++
        return ServiceViewHolder(adapterLayout, onItemClick)
    }

    fun filter(toSearch: String?, entireList: List<ServiceListElement>) {
        val filterList: MutableList<ServiceListElement> = ArrayList()
        if (toSearch != null && toSearch.isNotEmpty()) {
            for (item in entireList) {
                if (item.name.lowercase(Locale.ROOT).contains(toSearch.lowercase(Locale.ROOT))) {
                    Log.d("Searched field", toSearch.lowercase(Locale.ROOT))
                    Log.d("Choosed Item", item.name.lowercase(Locale.ROOT))
                    filterList += item
                }
            }
            dataset = filterList
        } else if (toSearch != null && toSearch.isEmpty()) {
            dataset = entireList
        }
        Log.d("ListToPrint", dataset.toString())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}