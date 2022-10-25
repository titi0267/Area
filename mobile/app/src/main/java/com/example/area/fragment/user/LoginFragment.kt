package com.example.area.fragment.user

import android.content.Intent
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
import com.example.area.activity.MainActivity
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.LoginFields
import com.example.area.repository.Repository
import com.example.area.utils.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        loginFocusListener(view)
        view.findViewById<Button>(R.id.request_button).setOnClickListener {
            lateinit var url: String
            // Store email and password
            val loginForm = LoginFields(
                view.findViewById<EditText>(R.id.login_email_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.login_password_field_edit_text).text.toString(),
            )
            // Login error handling
            try {
                url = urlParser(
                    view.findViewById<EditText>(R.id.login_ip_field_edit_text).text.toString(),
                    view.findViewById<EditText>(R.id.login_port_field_edit_text).text.toString()
                )
                checkLoginField(loginForm)
            }
            catch (e: IllegalArgumentException) {
                Toast.makeText(context as UserConnectionActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginRequest(url, loginForm)
        }
        return view
    }

    private fun loginRequest(url: String, loginForm: LoginFields)
    {
        var token: String?
        val sessionManager = SessionManager(context as UserConnectionActivity)
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        // Login post request
        viewModel.login(loginForm)
        // Receive login response
        viewModel.userResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    sessionManager.saveAuthToken("user_token", token!!)
                    sessionManager.saveAuthToken("url", url)
                }
                Toast.makeText(context as UserConnectionActivity, "Successfully logged in!", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(
                        context,
                        MainActivity::class.java
                    )
                )
            }
        })
    }

    private fun loginFocusListener(view: View)
    {
        textFieldsFocusListener(view, R.id.login_ip_field_edit_text, R.id.login_ip_field_layout, ::checkIp)
        textFieldsFocusListener(view, R.id.login_port_field_edit_text, R.id.login_port_field_layout, ::checkPort)
        textFieldsFocusListener(view, R.id.login_email_field_edit_text, R.id.login_email_field_layout, ::checkEmail)
        textFieldsFocusListener(view, R.id.login_password_field_edit_text, R.id.login_password_field_layout, ::checkPasswordLogin)
    }
}