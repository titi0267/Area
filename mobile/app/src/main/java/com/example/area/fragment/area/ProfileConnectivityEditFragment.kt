package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.model.UserInfo
import com.example.area.repository.Repository
import com.example.area.utils.*
import retrofit2.Response

class ProfileConnectivityEditFragment : Fragment(R.layout.fragment_profile_connectivity) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        connectivityFocusListener(view)
        view.findViewById<Button>(R.id.connectivity_test_connection_button).setOnClickListener {
            if ((context as AreaActivity).loading)
                return@setOnClickListener
            connectivityRequest(view, true)
        }
        view.findViewById<Button>(R.id.connectivity_apply_url_button).setOnClickListener {
            if ((context as AreaActivity).loading)
                return@setOnClickListener
            connectivityRequest(view, false)
        }
        return (view)
    }

    private fun connectivityRequest(view: View, test: Boolean) {
        val url = getUrlFromFields(view) ?: return
        val sessionManager = SessionManager(context as AreaActivity)
        val token = sessionManager.fetchAuthToken("user_token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<UserInfo>?> = Observer { response ->
            if ((context as AreaActivity).loading)
                return@Observer
            if (response == null) {
                Toast.makeText(context as AreaActivity, "Connection test failed!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context as AreaActivity, "Connection successful!", Toast.LENGTH_SHORT).show()
                if (!test) {
                    sessionManager.saveAuthToken("url", url)
                    (context as AreaActivity).onBackPressed()
                }
            }
        }
        viewModel.getUserInfo(token, context as AreaActivity, observer)
        viewModel.userInfoResponse.observe(viewLifecycleOwner, observer)
    }

    private fun getUrlFromFields(view: View): String? {
        return try {
            (urlParser(
                view.findViewById<EditText>(R.id.connectivity_ip_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.connectivity_port_field_edit_text).text.toString()
            ))
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context as AreaActivity, e.message, Toast.LENGTH_SHORT).show()
            (null)
        }
    }

    private fun connectivityFocusListener(view: View) {
        textFieldsFocusListener(
            view,
            R.id.connectivity_ip_field_edit_text,
            R.id.connectivity_ip_field_layout,
            ::checkIp
        )
        textFieldsFocusListener(
            view,
            R.id.connectivity_port_field_edit_text,
            R.id.connectivity_port_field_layout,
            ::checkPort
        )
    }
}