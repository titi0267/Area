package com.example.area.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
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
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return ItemViewHolder(adapterLayout, onItemClick)
        val drawable: GradientDrawable? = AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round_corner_on_layout) as GradientDrawable?
        val newDrawable: GradientDrawable? = drawable?.constantState?.newDrawable()?.mutate() as GradientDrawable?

        if (selectedItem == itemCount)
            selectedItem = 0
        newDrawable?.orientation = GradientDrawable.Orientation.TOP_BOTTOM
        newDrawable?.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(dataset[selectedItem].actionServiceName)), Color.parseColor(aboutClass.getServiceBackgroundColor(dataset[selectedItem].reactionServiceName)))
        adapterLayout.background = newDrawable
        adapterLayout.findViewById<TextView>(R.id.actionServiceNameInItemList).text = dataset[selectedItem].actionServiceName
        adapterLayout.findViewById<TextView>(R.id.reactionServiceNameInItemList).text = dataset[selectedItem].reactionServiceName
        adapterLayout.findViewById<TextView>(R.id.actionNameInItemList).text = dataset[selectedItem].actionName
        adapterLayout.findViewById<TextView>(R.id.actionNameInItemList).isSelected = true
        adapterLayout.findViewById<TextView>(R.id.reactionNameInItemList).text = dataset[selectedItem].reactionName
        adapterLayout.findViewById<TextView>(R.id.reactionNameInItemList).isSelected = true
        adapterLayout.findViewById<ImageView>(R.id.leftLogo).setImageDrawable(BitmapDrawable(context.resources, dataset[selectedItem].actionServiceBitmap))
        adapterLayout.findViewById<ImageView>(R.id.rightLogo).setImageDrawable(BitmapDrawable(context.resources, dataset[selectedItem].reactionServiceBitmap))
        selectedItem++
        return ItemViewHolder(adapterLayout, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    }

    override fun getItemCount() = dataset.size
}