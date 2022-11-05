package com.example.area.fragment.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.area.activity.OAuthConnectionActivity
import com.example.area.adapter.OAuthServiceItemAdapter
import com.example.area.data.OAuthServiceDatasource
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager

class OAuthLinkingFragment : Fragment(R.layout.fragment_oauth_linking) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  super.onCreateView(inflater, container, savedInstanceState) ?: return null
        refreshList(view)
        view.findViewById<Button>(R.id.oauth_linking_refresh).setOnClickListener {
            refreshList(view)
        }
        return view
    }

    private fun refreshList(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.oauth_linking_recycler_view)
        val oauthServiceList = OAuthServiceDatasource()
        val aboutClass = ((context as AreaActivity).application as AREAApplication).aboutClass ?: return
        val servicesImages = ((context as AreaActivity).application as AREAApplication).aboutBitmapList ?: return
        val tokenTable = (((context as AreaActivity).application as AREAApplication).userInfo ?: return).tokensTable

        recycler.layoutManager = LinearLayoutManager(context as AreaActivity)
        oauthServiceList.clear()
        for (elem in aboutClass.getServiceList()) {
            if (elem.oauthName == null)
                continue
            oauthServiceList.addOauthService(elem.id, elem.name, elem.oauthName, servicesImages[elem.id - 1], tokenTable[elem.oauthName + "Token"] != null)
        }
        updateRecycler(recycler, oauthServiceList)
    }

    private fun updateRecycler(recycler: RecyclerView, oauthServiceList: OAuthServiceDatasource) {
        recycler.setHasFixedSize(true)
        recycler.adapter = OAuthServiceItemAdapter(context as AreaActivity, oauthServiceList.loadOauthServiceInfo())
    }
}