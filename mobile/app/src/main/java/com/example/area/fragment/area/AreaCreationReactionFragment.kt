package com.example.area.fragment.area

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.ActionReactionItemAdapter
import com.example.area.data.ActionReactionDatasource
import com.example.area.model.AREAFields
import com.example.area.model.ActionReactionInfo
import com.example.area.model.ServiceListElement
import com.example.area.model.Token
import com.example.area.model.about.AboutClass
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Response

class AreaCreationReactionFragment(private val actionService: ServiceListElement, private val action: ActionReactionInfo, private val reactionService: ServiceListElement) : Fragment(R.layout.fragment_area_creation_reaction) {
    private var reactionSelectedIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return view
        val servicesImages = ((context as AreaActivity).application as AREAApplication).aboutBitmapList ?: return view
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewReaction)
        val actionReactionList = ActionReactionDatasource()

        view.findViewById<ImageView>(R.id.selectedReactionServiceLogoShow).setImageDrawable(BitmapDrawable((context as AreaActivity).resources, servicesImages[reactionService.id - 1]))
        view.findViewById<TextView>(R.id.selectedReactionServiceTextShow).text = reactionService.name

        recycler.layoutManager = LinearLayoutManager(context as AreaActivity)
        view.findViewById<Button>(R.id.backFromReactionCreationButton).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        view.findViewById<Button>(R.id.areaRealCreationButton).setOnClickListener {
            onCreateButton(view)
        }
        actionReactionList.clear()
        for (elem in aboutClass.getReactionListFromServiceId(reactionService.id) ?: return view) {
            actionReactionList.addService(elem.id, elem.name, elem.reactionParamName, elem.description)
        }
        updateRecycler(recycler, actionReactionList)
        createSpinner(view, aboutClass)
        return view
    }

    private fun updateRecycler(recycler: RecyclerView, actionReactionList: ActionReactionDatasource) {
        recycler.setHasFixedSize(true)
        recycler.adapter = ActionReactionItemAdapter(context as AreaActivity, actionReactionList.loadActionReactionInfo()) {
                position -> onItemClick(position, actionReactionList.loadActionReactionInfo()[position].name)
        }
    }

    private fun onItemClick(position: Int, toPrint: String) {
        reactionSelectedIndex = position
        Toast.makeText(context as AreaActivity, "$toPrint selected", Toast.LENGTH_SHORT).show()
    }

    private fun onCreateButton(view: View) {
        val textHint = view.findViewById<TextInputEditText>(R.id.reactionParamText).text ?: return

        if (reactionSelectedIndex == -1) {
            Toast.makeText(context as AreaActivity, "Please select an reaction", Toast.LENGTH_SHORT).show()
            return
        }
        if (textHint.isEmpty()) {
            Toast.makeText(context as AreaActivity, "Please enter an reaction parameter", Toast.LENGTH_SHORT).show()
            return
        }
        createAreaRequest(textHint)
    }

    private fun createAreaRequest(textHint: Editable) {
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

        viewModel.areaCreation(token, AREAFields(actionService.id, action.id, action.paramName, reactionService.id, reactionSelectedIndex + 1, textHint.toString()), context as AreaActivity, observer)
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun createSpinner(view: View, aboutClass: AboutClass) {
        var first: Boolean = true
        val injectSpinner = view.findViewById<Spinner>(R.id.injectActionParamsSpinner)
        val injectedParams: ArrayAdapter<String> = ArrayAdapter(context as AreaActivity, android.R.layout.simple_spinner_item, aboutClass.getServiceActionAvailableInjectParamsById(actionService.id, action.id) ?: return)
        injectedParams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        injectSpinner.isClickable = true
        injectSpinner.adapter = injectedParams
        injectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>?, viewSpinner: View?, pos: Int, p3: Long) {
                val reactionParameterText: Editable = view.findViewById<TextInputEditText>(R.id.reactionParamText).text ?: return

                if (first) {
                    first = false
                    return
                }
                if (adapter == null)
                    return
                reactionParameterText.append("%${adapter.selectedItem}%")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}