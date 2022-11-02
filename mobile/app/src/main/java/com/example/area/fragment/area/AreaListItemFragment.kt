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
import androidx.recyclerview.widget.RecyclerView
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.data.Datasource
import com.example.area.model.ActionReaction
import com.example.area.model.AreaInfo
import com.example.area.model.EnableDisable
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager
import org.w3c.dom.Text
import androidx.lifecycle.Observer

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
        lateinit var enable: EnableDisable

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
            val message = if (isChecked) "Switch1:ON" else "Switch1:OFF"
            enable = EnableDisable(item.id, isChecked)
            viewModel.putEnableDisable(sessionManager.fetchAuthToken("user_token")!!, enable)
            viewModel.enableResponse.observe(context as AreaActivity, Observer { response ->
                if (response.isSuccessful) {
                    Toast.makeText(context as AreaActivity, (if (isChecked) "Enabled" else "Disabled"), Toast.LENGTH_SHORT).show()
                }
            })
            Toast.makeText(context as AreaActivity, message, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}