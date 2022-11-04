package com.example.area.fragment.area

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReaction
import com.example.area.model.EnableDisable
import com.example.area.model.about.About
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class AreaListItemFragment(private val item: ActionReaction) : Fragment(R.layout.fragment_area_list_item) {

    private var abt: About? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val about = AboutJsonCreator()
        val sessionManager = SessionManager(context as AreaActivity)
        val token = sessionManager.fetchAuthToken("user_token") ?: null
        var enable: EnableDisable
        val url = sessionManager.fetchAuthToken("url") ?: return view
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        var enabledStatus = 0

        viewModel = ViewModelProvider(context as AreaActivity, viewModelFactory)[MainViewModel::class.java]
        view.findViewById<Button>(R.id.backFromAreaListItemButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        about.getAboutJson(context as AreaActivity, this, this) {
            abt = about.liveDataResponse.value
            if (abt != null) {
                view.findViewById<TextView>(R.id.actionServiceTextInItem).text = abt!!.server.services[item.actionServiceId-1].name
                view.findViewById<TextView>(R.id.actionNameInItem).text = abt!!.server.services[item.actionServiceId-1].actions[item.actionId-1].name
                view.findViewById<TextView>(R.id.actionParamInItem).text = item.actionParam
                view.findViewById<TextView>(R.id.reactionServiceTextInItem).text = abt!!.server.services[item.reactionServiceId-1].name
                view.findViewById<TextView>(R.id.reactionNameInItem).text = abt!!.server.services[item.reactionServiceId-1].reactions[item.reactionId-1].name
                view.findViewById<TextView>(R.id.reactionParamInItem).text = item.reactionParam
            }
        }
        view.findViewById<Switch>(R.id.enableItemListSwitch).setOnCheckedChangeListener { _, isChecked ->
            enable = EnableDisable(item.id, isChecked)
            if (token != null) {
                enabledStatus=0
                viewModel.putEnableDisable(token, enable)
                viewModel.enableResponse.observe(context as AreaActivity, Observer { response ->
                    if (response.isSuccessful) {
                        if (enabledStatus == 0) {
                            Toast.makeText(context as AreaActivity, (if (isChecked) "Enabled" else "Disabled"), Toast.LENGTH_SHORT).show()
                            enabledStatus++
                        }
                    }
                })
            }
        }
        view.findViewById<Button>(R.id.deleteItemListButton).setOnClickListener {
            if (token != null) {
                viewModel.deleteArea(token, item.id)
                viewModel.deleteAreaResponse.observe(context as AreaActivity, Observer { response ->
                    if (response.isSuccessful) {
                        Toast.makeText(context as AreaActivity, "Area successfully deleted", Toast.LENGTH_SHORT).show()
                        (context as AreaActivity).changeFragment(AreaListFragment(), "area_list")
                    }
                    Log.d("Delete response", response.toString())
                })

            }
        }
        return view
    }
}