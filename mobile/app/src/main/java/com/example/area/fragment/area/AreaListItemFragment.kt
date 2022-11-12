package com.example.area.fragment.area

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.hardware.Camera.Area
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.example.area.model.EditActionReaction
import com.example.area.repository.Repository
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Response

class AreaListItemFragment(private val item: ActionReaction) : Fragment(R.layout.fragment_area_list_item) {

    private var abt: About? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var enable: EnableDisable
    private var enabledStatus = 0
    private var oldActionParam = item.actionParam
    private var oldReactionParam = item.reactionParam

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
        val drawable: GradientDrawable = (((AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round) ?: return view).constantState ?: return view).newDrawable().mutate()) as GradientDrawable // To change with other background
        val drawable2: GradientDrawable = (((AppCompatResources.getDrawable(context as AreaActivity, R.drawable.round) ?: return view).constantState ?: return view).newDrawable().mutate()) as GradientDrawable // To change with other background

        viewModel = ViewModelProvider(context as AreaActivity, viewModelFactory)[MainViewModel::class.java]
        view.findViewById<ImageView>(R.id.actionLogoEdit).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, aboutClass.getServiceImageURL(item.actionServiceId)))
        view.findViewById<ImageView>(R.id.reactionLogoEdit).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, aboutClass.getServiceImageURL(item.reactionServiceId)))
        drawable.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(item.actionServiceId)), Color.parseColor(aboutClass.getServiceBackgroundColor(item.actionServiceId)))
        view.findViewById<ConstraintLayout>(R.id.constraintLayout).background = drawable
        drawable2.colors = intArrayOf(Color.parseColor(aboutClass.getServiceBackgroundColor(item.reactionServiceId)), Color.parseColor(aboutClass.getServiceBackgroundColor(item.reactionServiceId)))
        view.findViewById<ConstraintLayout>(R.id.constraintLayout2).background = drawable2
        view.findViewById<TextView>(R.id.actionServiceTextInItem).text = aboutClass.getServiceNameById(item.actionServiceId)
        view.findViewById<TextView>(R.id.actionNameInItem).text = aboutClass.getServiceActionNameById(item.actionServiceId, item.actionId)
        view.findViewById<TextView>(R.id.actionParamInput).text = item.actionParam
        view.findViewById<TextView>(R.id.reactionServiceTextInItem).text = aboutClass.getServiceNameById(item.reactionServiceId)
        view.findViewById<TextView>(R.id.reactionNameInItem).text = aboutClass.getServiceReactionNameById(item.reactionServiceId, item.reactionId)
        view.findViewById<TextView>(R.id.reactionParamInput).text = item.reactionParam
        view.findViewById<SwitchMaterial>(R.id.enableItemListSwitch).setOnCheckedChangeListener { _, isChecked ->
            onEnableDisableSwitch(isChecked, token)
        }
        view.findViewById<Button>(R.id.deleteItemListButton).setOnClickListener {
            onDeleteButton(token)
        }
        view.findViewById<Button>(R.id.editAreaItemButton).setOnClickListener {
            onEditButton(token, view.findViewById<TextInputEditText>(R.id.actionParamInput).text.toString(), view.findViewById<TextInputEditText>(R.id.reactionParamInput).text.toString())
        }
        return view
    }

    private fun onEditButton(token: String?, actionParam: String?, reactionParam: String?) {
        lateinit var edit: EditActionReaction
        val observer: Observer<Response<ActionReaction>?> = Observer { response ->
            if (response == null || actionParam == null || reactionParam == null)
                return@Observer
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, "Area successfully edited", Toast.LENGTH_SHORT).show()
                oldActionParam = actionParam
                oldReactionParam = reactionParam
            }
        }

        if (token == null)
            return
        if (actionParam == null || reactionParam == null)
            return
        if (checkChangedParam(false, actionParam, null) || checkChangedParam(true, null, reactionParam)) {
            edit = EditActionReaction(item.id, actionParam, reactionParam)
            viewModel.putEditArea(token, edit, context as AreaActivity, observer)
            viewModel.editAreaResponse.observe(context as AreaActivity, observer)
        }
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
            Log.d("Delete", response.toString())
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

    private fun checkChangedParam(status: Boolean, action: String?, reaction: String?): Boolean {
        return if (!status) {
            oldActionParam != action
        } else {
            oldReactionParam != reaction
        }
    }
}