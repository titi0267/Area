package com.example.area.fragment.area

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ItemAdapter
import com.example.area.adapter.MarginItemDecoration
import com.example.area.data.Datasource
import com.example.area.model.ActionReaction
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import retrofit2.Response

class AreaListFragment : Fragment(R.layout.fragment_area_list) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)

        updateRecycler(recycler, Datasource(), null)
        areaListRequest(recycler)
        /*view.findViewById<Button>(R.id.area_list_fetch).setOnClickListener {
            areaListRequest(recycler)
        }*/ //Code for refresh in comment in case we implement it
        return view
    }

    private fun areaListRequest(recycler: RecyclerView) {
        val sessionManager = SessionManager(context as AreaActivity)
        val token = sessionManager.fetchAuthToken("user_token") ?: return
        val url = sessionManager.fetchAuthToken("url") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return
        val servicesImages = ((context as AreaActivity).application as AREAApplication).aboutBitmapList ?: return
        val myDataSet = Datasource()
        val observer: Observer<Response<List<ActionReaction>>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                val jsonArray: List<ActionReaction> = response.body()!!
                myDataSet.clear()
                for (item in jsonArray) {
                    myDataSet.addArea(
                        servicesImages[item.actionServiceId - 1],
                        servicesImages[item.reactionServiceId - 1],
                        aboutClass.getServiceActionNameById(item.actionServiceId, item.actionId) ?: continue,
                        aboutClass.getServiceReactionNameById(item.reactionServiceId, item.reactionId) ?: continue,
                        aboutClass.getServiceNameById(item.actionServiceId) ?: continue,
                        aboutClass.getServiceNameById(item.reactionServiceId) ?: continue
                    )
                }
                updateRecycler(recycler, myDataSet, jsonArray)
            }
        }

        recycler.addItemDecoration(MarginItemDecoration(50))
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getUserAreaList(token, context as AreaActivity, observer)
        viewModel.listAREAResponse.observe(viewLifecycleOwner, observer)
    }

    private fun updateRecycler(recycler: RecyclerView, myDataSet: Datasource, jsonArray: List<ActionReaction>?) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ItemAdapter(context as AreaActivity, myDataSet.getAreaInfo()) { position -> onItemClick(position, jsonArray?.get(position)) }
    }

    private fun onItemClick(position: Int, item: ActionReaction?) {
        (context as AreaActivity).changeFragment(AreaListItemFragment(item!!), "area_list_item")
    }
}