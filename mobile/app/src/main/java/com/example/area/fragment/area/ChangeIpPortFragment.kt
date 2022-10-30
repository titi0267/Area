package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.UserConnectionActivity
import com.example.area.utils.*

class ChangeIpPortFragment : Fragment(R.layout.fragment_change_ip_port) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val sessionManager = SessionManager(context as AreaActivity)

        view.findViewById<Button>(R.id.profile_edit_ip_port_confirmation).setOnClickListener {
            lateinit var url: String

            try {
                url = urlParser(
                    view.findViewById<EditText>(R.id.profile_edit_ip_field_edit_text).text.toString(),
                    view.findViewById<EditText>(R.id.profile_edit_port_field_edit_text).text.toString()
                )
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    context as AreaActivity,
                    "Error: " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            sessionManager.replaceAuthToken("url", url)
            Toast.makeText(context as AreaActivity, "Successfully changed IP/Port", Toast.LENGTH_SHORT).show()
            (context as AreaActivity).onBackPressed()
        }
        editIpPortFocusListener(view)
        return view
    }

    private fun editIpPortFocusListener(view: View) {
        textFieldsFocusListener(
            view,
            R.id.profile_edit_ip_field_edit_text,
            R.id.profile_edit_ip_field_layout,
            ::checkIp
        )
        textFieldsFocusListener(
            view,
            R.id.profile_edit_port_field_edit_text,
            R.id.profile_edit_port_field_layout,
            ::checkPort
        )
    }
}