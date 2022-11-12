package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.adapter.OAuthServiceItemAdapter
import com.example.area.data.OAuthServiceDatasource
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OAuthLinkingFragment : Fragment(R.layout.fragment_oauth_linking) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  super.onCreateView(inflater, container, savedInstanceState) ?: return null
        refreshList(view)
        view.findViewById<FloatingActionButton>(R.id.oauth_linking_refresh).setOnClickListener {
            refreshList(view)
        }
        return view
    }

    fun refreshList(viewArg: View?) {
        val usedView = viewArg ?: view ?: return
        val recycler = usedView.findViewById<RecyclerView>(R.id.oauth_linking_recycler_view)
        val oauthServiceList = OAuthServiceDatasource()
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return
        val servicesImages = ((context as AreaActivity).application as AREAApplication).aboutBitmapList ?: return
        val tokenTable = (((context as AreaActivity).application as AREAApplication).userInfo ?: return).tokensTable

        recycler.layoutManager = LinearLayoutManager(context as AreaActivity)
        oauthServiceList.clear()
        for (elem in aboutClass.getServiceList()) {
            if (elem.oauthName == null)
                continue
            oauthServiceList.addOauthService(elem.id, elem.name, elem.oauthName, servicesImages[elem.id - 1], elem.backgroundColor, tokenTable[elem.oauthName + "Token"] != null)
        }
        updateRecycler(recycler, oauthServiceList)
    }

    private fun updateRecycler(recycler: RecyclerView, oauthServiceList: OAuthServiceDatasource) {
        recycler.setHasFixedSize(true)
        recycler.adapter = OAuthServiceItemAdapter(context as AreaActivity, oauthServiceList.loadOauthServiceInfo())
    }
}