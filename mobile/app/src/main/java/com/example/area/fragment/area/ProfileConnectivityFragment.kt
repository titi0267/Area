package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.utils.SessionManager
import com.google.android.material.textview.MaterialTextView

class ProfileConnectivityFragment : Fragment(R.layout.fragment_profile_connectivity) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)
        val ipPortUrl = (sessionManager.fetchAuthToken("url") ?: return null).dropLast(1).drop(7)
        val ip: String = ipPortUrl.replaceAfter(':', "").dropLast(1)
        val port: String = ipPortUrl.replaceBefore(':', "").drop(1)

        view.findViewById<MaterialTextView>(R.id.profile_current_ip_text_value).text = ip
        view.findViewById<MaterialTextView>(R.id.profile_current_port_text_value).text = port
        view.findViewById<Button>(R.id.profile_change_ip_port_button).setOnClickListener {
            (context as AreaActivity).changeFragment(ProfileConnectivityEditFragment(), "change_ip_port")
        }
        return view
    }
}