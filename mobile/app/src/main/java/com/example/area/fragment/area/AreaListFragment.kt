package com.example.area.fragment.area

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager

class AreaListFragment : Fragment(R.layout.fragment_area_list) {

    private lateinit var viewModel: MainViewModel

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

        recycler.adapter = ItemAdapter(
            context as AreaActivity,
            myDataSet.loadAreaInfo()
        ) { position -> onItemClick(position) }
        recycler.setHasFixedSize(true)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
        viewModel.userResponse2.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                val jsonArray: List<ActionReaction> = response.body()!!
                myDataSet.clear()
                for (item in jsonArray) {
                    myDataSet.addArea(
                        item.actionServiceId,
                        item.reactionServiceId,
                        item.actionId.toString(),
                        item.reactionId.toString()
                    )
                }
                recycler.adapter = ItemAdapter(
                    context as AreaActivity,
                    myDataSet.loadAreaInfo()
                ) { position -> onItemClick(position) }
                recycler.setHasFixedSize(true)
            }
        })
        view.findViewById<Button>(R.id.areaCreationButton).setOnClickListener {
            (context as AreaActivity).changeFragment(AreaCreationActionServiceFragment(), "action_service_creation")
        }
        view.findViewById<Button>(R.id.backFromAreaListButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.area_list_fetch).setOnClickListener {
            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getUserAreaList(sessionManager.fetchAuthToken("user_token")!!)
            viewModel.userResponse2.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    val jsonArray: List<ActionReaction> = response.body()!!
                    myDataSet.clear()
                    for (item in jsonArray) {
                        myDataSet.addArea(
                            item.actionServiceId,
                            item.reactionServiceId,
                            item.actionId.toString(),
                            item.reactionId.toString()
                        )
                    }
                    recycler.adapter = ItemAdapter(
                        context as AreaActivity,
                        myDataSet.loadAreaInfo()
                    ) { position -> onItemClick(position) }
                    recycler.setHasFixedSize(true)
                }
            })
        }
        return view
    }

    private fun onItemClick(position: Int) {
        //startActivity(Intent(applicationContext, AreaListItemActivity::class.java))
    }
}