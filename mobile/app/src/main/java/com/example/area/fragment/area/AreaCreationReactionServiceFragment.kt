package com.example.area.fragment.area

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.MarginItemDecoration
import com.example.area.adapter.ServiceItemAdapter
import com.example.area.data.ServiceDatasource
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.about.AboutClass

class AreaCreationReactionServiceFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo) : Fragment(R.layout.fragment_area_creation_reaction_service) {
    private lateinit var serviceAdapter: ServiceItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val servicesImages = ((context as AreaActivity).application as AREAApplication).aboutBitmapList ?: return view
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewReactionService)
        val serviceList = ServiceDatasource()

        recycler.layoutManager = GridLayoutManager(context as AreaActivity, 2)
        feelSearchedList(serviceList, feelEntireList(aboutClass, servicesImages))
        view.findViewById<SearchView>(R.id.searchReactionService).setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(toSearch: String?): Boolean {
                onQuery(toSearch, aboutClass, servicesImages, serviceList, recycler)
                return false
            }
            override fun onQueryTextChange(toSearch: String?): Boolean {
                onQuery(toSearch, aboutClass, servicesImages, serviceList, recycler)
                return false
            }
        })
        updateRecycler(recycler, serviceList)
        recycler.addItemDecoration(MarginItemDecoration(50))
        return view
    }

    private fun onQuery(toSearch: String?, aboutClass: AboutClass, servicesImages: List<Bitmap>, serviceList: ServiceDatasource, recycler: RecyclerView) {
        serviceAdapter.filter(toSearch, feelEntireList(aboutClass, servicesImages))
        serviceList.clear()
        feelSearchedList(serviceList, serviceAdapter.getDataset())
        updateRecycler(recycler, serviceList)
    }

    private fun feelSearchedList(serviceList: ServiceDatasource, toFeedList: List<ServiceListElement>) {
        serviceList.clear()
        for (elem in toFeedList)
            serviceList.addService(elem)
    }

    private fun updateRecycler(recycler: RecyclerView, serviceList: ServiceDatasource) {
        serviceAdapter = ServiceItemAdapter(
            context as AreaActivity,
            serviceList.loadServiceInfo()
        ) { position -> onItemClick(position, serviceList) }
        recycler.setHasFixedSize(true)
        recycler.adapter = serviceAdapter
    }

    private fun onItemClick(position: Int, serviceList: ServiceDatasource) {
        (context as AreaActivity).changeFragment(AreaCreationReactionFragment(actionService, action, serviceList.loadServiceInfo()[position]), "reaction_creation")
    }

    private fun feelEntireList(aboutClass: AboutClass, servicesImages: List<Bitmap>): List<ServiceListElement> {
        val list = mutableListOf<ServiceListElement>()
        for (elem in aboutClass.getServiceList()) {
            if (elem.actions.isNotEmpty())
                list += ServiceListElement(elem.id, elem.name, servicesImages[elem.id - 1])
        }
        return list
    }
}