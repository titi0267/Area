package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.AREAFields
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.AboutJsonCreator
import com.example.area.utils.SessionManager

class AreaCreationFragment : Fragment(R.layout.fragment_area_creation),
    AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private var abt: About? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        val about = AboutJsonCreator()
        val rep = Repository(sessionManager.fetchAuthToken("url")!!)
        val viewModelFactory = MainViewModelFactory(rep)

        about.getAboutJson(context as AreaActivity, this, this) {
            abt = about.liveDataResponse.value
            var serviceList = arrayOf<String>()
            val actServSpi = view.findViewById<Spinner>(R.id.actionServiceSpinner)
            val reactServSpi = view.findViewById<Spinner>(R.id.reactionServiceSpinner)
            abt ?: return@getAboutJson
            Toast.makeText(context as AreaActivity, "Got about", Toast.LENGTH_SHORT).show()
            for (elem in abt!!.server.services) {
                serviceList += elem.name
            }
            if (serviceList.isNotEmpty()) {
                Toast.makeText(context as AreaActivity, "Hello", Toast.LENGTH_SHORT).show()
                actServSpi.adapter = ArrayAdapter(
                    context as AreaActivity,
                    R.layout.layout_spinner,
                    R.id.textSpinner, serviceList
                )
                reactServSpi.adapter = ArrayAdapter(
                    context as AreaActivity,
                    R.layout.layout_spinner,
                    R.id.textSpinner, serviceList
                )
                view.findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
                    viewModel.areaCreation(
                        sessionManager.fetchAuthToken("user_token")!!,
                        AREAFields(1, 1, 2, "yY1vTll0O1w", 3, 1, "Nouveau like!")
                    )
                    viewModel.userResponse.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            Toast.makeText(
                                context as AreaActivity,
                                "Area added successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                actServSpi.onItemSelectedListener = this@AreaCreationFragment
                reactServSpi.onItemSelectedListener = this@AreaCreationFragment
            }
        }
        view.findViewById<Button>(R.id.backFromCreationButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        return view
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        view?: return
        var actionList = arrayOf<String>()
        var reactionList = arrayOf<String>()
        val actionSpinner = requireView().findViewById<Spinner>(R.id.actionServiceSpinner)
        val reactionSpinner = requireView().findViewById<Spinner>(R.id.reactionServiceSpinner)
        if (abt != null) {
            for (elem in abt!!.server.services[actionSpinner.selectedItemPosition].actions) {
                actionList += elem.name
            }
        }
        requireView().findViewById<Spinner>(R.id.actionSpinner).adapter = ArrayAdapter(context as AreaActivity,
            R.layout.layout_spinner,
            R.id.textSpinner, actionList)
        if (abt != null) {
            for (elem in abt!!.server.services[reactionSpinner.selectedItemPosition].reactions) {
                reactionList += elem.name
            }
        }
        requireView().findViewById<Spinner>(R.id.reactionSpinner).adapter = ArrayAdapter(context as AreaActivity,
            R.layout.layout_spinner,
            R.id.textSpinner, reactionList)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}