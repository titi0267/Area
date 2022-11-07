package com.example.area.fragment.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.LoginFields
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.*
import retrofit2.Response

class ConnectivityFragment : Fragment(R.layout.fragment_connectivity) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        connectivityFocusListener(view)
        view.findViewById<Button>(R.id.connectivity_test_connection_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            testConnection(view)
        }
        view.findViewById<Button>(R.id.connectivity_apply_url_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            applyURL(view)
        }
        return (view)
    }

    private fun testConnection(view: View) {
        val url = getUrlFromFields(view) ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<Token>?> = Observer { response ->
            if ((context as UserConnectionActivity).loading)
                return@Observer
            if (response == null) {
                Toast.makeText(
                    context as UserConnectionActivity,
                    "Connection test failed!",
                    LENGTH_SHORT
                ).show()
            }
            else {
                Toast.makeText(context as UserConnectionActivity, "Connection successful!", LENGTH_SHORT).show()
            }
        }
        viewModel.login(LoginFields("", ""), context as UserConnectionActivity, observer)
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun applyURL(view: View) {
        val url = getUrlFromFields(view) ?: return
        val sessionManager = SessionManager(context as UserConnectionActivity)
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<Token>?> = Observer { response ->
            if ((context as UserConnectionActivity).loading)
                return@Observer
            if (response == null) {
                Toast.makeText(
                    context as UserConnectionActivity,
                    "Connection test failed!",
                    LENGTH_SHORT
                ).show()
            }
            else {
                Toast.makeText(context as UserConnectionActivity, "Connection successful!", LENGTH_SHORT).show()
                sessionManager.saveAuthToken("url", url)
                (context as UserConnectionActivity).changeFragment(NotLoggedInFragment(), "not_logged_in")
            }
        }
        viewModel.login(LoginFields("", ""), context as UserConnectionActivity, observer)
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun getUrlFromFields(view: View): String? {
        return try {
            (urlParser(
                view.findViewById<EditText>(R.id.connectivity_ip_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.connectivity_port_field_edit_text).text.toString()
            ))
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context as UserConnectionActivity, e.message, LENGTH_SHORT).show()
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