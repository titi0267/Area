package com.example.area.fragment.area

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.fragment.overview.OverviewItemFragment
import com.example.area.fragment.overview.OverviewItemWithoutParamFragment
import com.example.area.model.AREAFields
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import retrofit2.Response

class AreaCreationOverviewFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo, private val reactionService: ServiceListElement, private val reaction: ActionReactionInfo) : Fragment(R.layout.fragment_area_creation_overview) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        setOverviewFragment(R.id.overview_action_fragment_container, overviewFragmentPicker("Action", actionService, action), "overview_action_fragment")
        setOverviewFragment(R.id.overview_reaction_fragment_container, overviewFragmentPicker("Reaction", reactionService, reaction), "overview_reaction_fragment")
        view.findViewById<Button>(R.id.overview_create_button).setOnClickListener {
            createArea()
        }
        return view
    }

    private fun createArea() {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val token = sessionManager.fetchAuthToken("user_token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val observer : Observer<Response<Token>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                Toast.makeText(context as AreaActivity, "Area added successfully!", Toast.LENGTH_SHORT).show()
                (context as AreaActivity).changeFragment(AreaListFragment(), "area_list")
            }
        }

        viewModel.areaCreation(token, AREAFields(actionService.id, action.id, action.paramName, reactionService.id, reaction.id, reaction.paramName), context as AreaActivity, observer)
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun overviewFragmentPicker(type: String, service: ServiceListElement, actionReactionInfo: ActionReactionInfo) : Fragment {
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return Fragment()
        val paramName: String = if (type == "Action") { aboutClass.getServiceActionParamNameById(service.id, actionReactionInfo.id) ?: "" } else { aboutClass.getServiceReactionParamNameById(service.id, actionReactionInfo.id) ?: "" }

        if (paramName.isNotEmpty()) {
            return OverviewItemFragment(type, service, actionReactionInfo)
        }
        return OverviewItemWithoutParamFragment(type, service, actionReactionInfo)
    }

    private fun setOverviewFragment(fragmentContainer: Int, fragment: Fragment, tag: String?) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add(fragmentContainer, fragment, tag)
        }
    }
}