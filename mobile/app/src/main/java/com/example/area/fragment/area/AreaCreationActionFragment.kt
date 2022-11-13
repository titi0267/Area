package com.example.area.fragment.area

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ActionReactionItemAdapter
import com.example.area.data.ActionReactionDatasource
import com.example.area.model.ServiceListElement
import com.example.area.model.about.AboutClass
import com.google.android.material.textfield.TextInputEditText

class AreaCreationActionFragment(private val actionService: ServiceListElement) : Fragment(R.layout.fragment_area_creation_action) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewAction)
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val actionReactionList = ActionReactionDatasource()
        val drawable: GradientDrawable? = AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round_service_background) as GradientDrawable?
        val newDrawable: GradientDrawable? = drawable?.constantState?.newDrawable()?.mutate() as GradientDrawable?

        newDrawable?.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(actionService.id)), Color.parseColor(aboutClass.getServiceBackgroundColor(actionService.id)))
        view.findViewById<View>(R.id.constraintLayoutActionCreationTitle).background = newDrawable
        view.findViewById<ImageView>(R.id.selectedActionServiceLogoShow).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, actionService.imageBitmap))
        view.findViewById<TextView>(R.id.selectedActionServiceTextShow).text = actionService.name

        recycler.layoutManager = GridLayoutManager(context as AreaActivity, 2)
        actionReactionList.clear()
        for (elem in aboutClass.getActionListFromServiceId(actionService.id) ?: return view) {
            actionReactionList.addService(elem.id, elem.name, elem.actionParamName, elem.description)
        }
        updateRecycler(recycler, actionReactionList, actionService.id, aboutClass)
        //recycler.addItemDecoration(MarginItemDecoration(50)) /* Line to uncomment when the MarginItemDecoration class is merged (it applies margin to the items) */
        return view
    }

    private fun updateRecycler(recycler: RecyclerView, actionReactionList: ActionReactionDatasource, serviceId: Int, aboutClass: AboutClass) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ActionReactionItemAdapter(context as AreaActivity, actionReactionList.loadActionReactionInfo(), serviceId, true) {
                position -> onItemClick(position, actionReactionList, aboutClass)
        }
    }

    private fun onItemClick(position: Int, actionReactionList: ActionReactionDatasource, aboutClass: AboutClass) {
        if (aboutClass.getServiceActionParamNameById(actionService.id, actionReactionList.loadActionReactionInfo()[position].id)?.isEmpty() == true)
            (context as AreaActivity).changeFragment(AreaCreationReactionServiceFragment(actionService, actionReactionList.loadActionReactionInfo()[position]), "reaction_service_creation")
        else
            (context as AreaActivity).changeFragment(AreaCreationActionParamFragment(actionService, actionReactionList.loadActionReactionInfo()[position]), "action_param_creation")
    }
}