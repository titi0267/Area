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
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.*

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        val button = view.findViewById<Button>(R.id.request_button)

        registerFocusListener(view)
        button.setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            lateinit var url: String
            //Store register fields
            val registerForm = RegisterFields(
                view.findViewById<EditText>(R.id.register_first_name_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_last_name_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_email_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_password_field_edit_text).text.toString()
            )
            // Check registration validity
            try {
                url = urlParser(
                    view.findViewById<EditText>(R.id.register_ip_field_edit_text).text.toString(),
                    view.findViewById<EditText>(R.id.register_port_field_edit_text).text.toString()
                )
                checkRegisterField(registerForm)
            }
            catch (e: IllegalArgumentException) {
                Toast.makeText(context as UserConnectionActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerRequest(url, registerForm)
        }

        return view
    }

    private fun registerRequest(url: String, registerForm: RegisterFields) {
        var token: String?
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val sessionManager = SessionManager(context as UserConnectionActivity)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.register(registerForm, context as UserConnectionActivity)
        viewModel.userResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    sessionManager.saveAuthToken("user_token", token!!)
                    sessionManager.saveAuthToken("url", url)
                }
                Toast.makeText(
                    context as UserConnectionActivity,
                    "Successfully logged in!",
                    Toast.LENGTH_SHORT
                ).show()
                // Redirect to main activity
                startActivity(
                    Intent(
                        context,
                        MainActivity::class.java
                    )
                )
            }
        })
    }

    private fun registerFocusListener(view: View)
    {
        textFieldsFocusListener(view, R.id.register_ip_field_edit_text, R.id.register_ip_field_layout, ::checkIp)
        textFieldsFocusListener(view, R.id.register_port_field_edit_text, R.id.register_port_field_layout, ::checkPort)
        textFieldsFocusListener(view, R.id.register_first_name_field_edit_text, R.id.register_first_name_field_layout, ::checkNames)
        textFieldsFocusListener(view, R.id.register_last_name_field_edit_text, R.id.register_last_name_field_layout, ::checkNames)
        textFieldsFocusListener(view, R.id.register_email_field_edit_text, R.id.register_email_field_layout, ::checkEmail)
        textFieldsFocusListener(view, R.id.register_password_field_edit_text, R.id.register_password_field_layout, ::checkPasswordRegister)
    }
}