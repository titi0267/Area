package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ItemAdapter
import com.example.area.data.Datasource
import com.example.area.model.ActionReaction
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager

class AreaListFragment : Fragment(R.layout.fragment_area_list) {

    private lateinit var viewModel: MainViewModel
    private var abt: About? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val myDataSet = Datasource()
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        val sessionManager = SessionManager(context as AreaActivity)
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)
        val about = AboutJsonCreator()

        recycler.adapter = ItemAdapter(
            context as AreaActivity,
            myDataSet.loadAreaInfo()
        ) { position -> onItemClick(position, null) }
        recycler.setHasFixedSize(true)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
        about.getAboutJson(context as AreaActivity, this, this) {
            abt = about.liveDataResponse.value
            if (abt != null) {
                viewModel.userResponse2.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {
                        val jsonArray: List<ActionReaction> = response.body()!!
                        myDataSet.clear()
                        for (item in jsonArray) {
                            myDataSet.addArea(
                                abt!!.server.services[item.actionServiceId-1].imageUrl,
                                abt!!.server.services[item.reactionServiceId-1].imageUrl,
                                abt!!.server.services[item.actionServiceId-1].actions[item.actionId-1].name,
                                abt!!.server.services[item.reactionServiceId-1].reactions[item.reactionId-1].name
                            )
                        }
                        recycler.adapter = ItemAdapter(
                            context as AreaActivity,
                            myDataSet.loadAreaInfo()
                        ) { position -> onItemClick(position, jsonArray[position]) }
                        recycler.setHasFixedSize(true)
                    }
                })
            }
        }
        view.findViewById<Button>(R.id.areaCreationButton).setOnClickListener {
            (context as AreaActivity).changeFragment(AreaCreationActionServiceFragment(), "action_service_creation")
        }
        view.findViewById<Button>(R.id.backFromAreaListButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.area_list_fetch).setOnClickListener {
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
            about.getAboutJson(context as AreaActivity, this, this) {
                abt = about.liveDataResponse.value
                if (abt != null) {
                    viewModel.userResponse2.observe(viewLifecycleOwner, Observer { response ->
                        if (response.isSuccessful) {
                            val jsonArray: List<ActionReaction> = response.body()!!
                            myDataSet.clear()
                            for (item in jsonArray) {
                                myDataSet.addArea(
                                    abt!!.server.services[item.actionServiceId-1].imageUrl,
                                    abt!!.server.services[item.reactionServiceId-1].imageUrl,
                                    abt!!.server.services[item.actionServiceId-1].actions[item.actionId-1].name,
                                    abt!!.server.services[item.reactionServiceId-1].reactions[item.reactionId-1].name
                                )
                            }
                            recycler.adapter = ItemAdapter(
                                context as AreaActivity,
                                myDataSet.loadAreaInfo()
                            ) { position -> onItemClick(position, jsonArray[position])}
                            recycler.setHasFixedSize(true)
                        }
                    })
                }
            }
        }
        return view
    }

    private fun onItemClick(position: Int, item: ActionReaction?) {
        (context as AreaActivity).changeFragment(AreaListItemFragment(item!!), "area_list_item")
    }
}