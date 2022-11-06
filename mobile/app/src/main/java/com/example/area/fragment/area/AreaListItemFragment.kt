package com.example.area.fragment.area

import android.os.Bundle
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
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.ActionReaction
import com.example.area.model.EnableDisable
import com.example.area.model.about.About
import com.example.area.utils.SessionManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.repository.Repository
import retrofit2.Response

class AreaListItemFragment(private val item: ActionReaction) : Fragment(R.layout.fragment_area_list_item) {

    private var abt: About? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var enable: EnableDisable
    private var enabledStatus = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        val token = sessionManager.fetchAuthToken("user_token") ?: return view
        val url = sessionManager.fetchAuthToken("url") ?: return view
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view

        viewModel = ViewModelProvider(context as AreaActivity, viewModelFactory)[MainViewModel::class.java]
        view.findViewById<Button>(R.id.backFromAreaListItemButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<TextView>(R.id.actionServiceTextInItem).text = aboutClass.getServiceNameById(item.actionServiceId)
        view.findViewById<TextView>(R.id.actionNameInItem).text = aboutClass.getServiceActionNameById(item.actionServiceId, item.actionId)
        view.findViewById<TextView>(R.id.actionParamInItem).text = item.actionParam
        view.findViewById<TextView>(R.id.reactionServiceTextInItem).text = aboutClass.getServiceNameById(item.reactionServiceId)
        view.findViewById<TextView>(R.id.reactionNameInItem).text = aboutClass.getServiceReactionNameById(item.reactionServiceId, item.reactionId)
        view.findViewById<TextView>(R.id.reactionParamInItem).text = item.reactionParam
        view.findViewById<Switch>(R.id.enableItemListSwitch).setOnCheckedChangeListener { _, isChecked ->
            onEnableDisableSwitch(isChecked, token)
        }
        view.findViewById<Button>(R.id.deleteItemListButton).setOnClickListener {
            onDeleteButton(token)
        }
        return view
    }

    private fun onEnableDisableSwitch(isChecked: Boolean, token: String?) {
        val observer: Observer<Response<ActionReaction>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful && enabledStatus == 0) {
                Toast.makeText(context as AreaActivity, (if (isChecked) "Enabled" else "Disabled"), Toast.LENGTH_SHORT).show()
                enabledStatus++
            }
        }
        enable = EnableDisable(item.id, isChecked)
        if (token == null)
            return
        enabledStatus=0
        viewModel.putEnableDisable(token, enable, context as AreaActivity, observer)
        viewModel.enableResponse.observe(context as AreaActivity, observer)
    }

    private fun onDeleteButton(token: String?) {
        val observer: Observer<Response<ActionReaction>?> = Observer { response ->
            if (response == null) {
                return@Observer
            }
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, "Area successfully deleted", Toast.LENGTH_SHORT).show()
                (context as AreaActivity).changeFragment(AreaListFragment(), "area_list")
            }
        }

        if (token != null) {
            viewModel.deleteArea(token, item.id, context as AreaActivity, observer)
            viewModel.deleteAreaResponse.observe(context as AreaActivity, observer)
        }
    }
}