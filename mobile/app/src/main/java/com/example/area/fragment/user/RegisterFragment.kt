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
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.example.area.utils.checkRegisterField
import com.example.area.utils.urlParser

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<Button>(R.id.request_button).setOnClickListener {
            lateinit var url: String
            //Store register fields
            val registerForm = RegisterFields(
                view.findViewById<EditText>(R.id.first_name_field).text.toString(),
                view.findViewById<EditText>(R.id.last_name_field).text.toString(),
                view.findViewById<EditText>(R.id.email_field).text.toString(),
                view.findViewById<EditText>(R.id.password_field).text.toString()
            )
            // Check registration validity
            try {
                url = urlParser(
                    view.findViewById<EditText>(R.id.ip_field).text.toString(),
                    view.findViewById<EditText>(R.id.port_field).text.toString()
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

    private fun registerRequest(url: String, registerForm: RegisterFields)
    {
        var token: String?
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val sessionManager = SessionManager(context as UserConnectionActivity)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.register(registerForm)
        viewModel.userResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    sessionManager.saveAuthToken("user_token", token!!)
                    sessionManager.saveAuthToken("url", url)
                }
                Toast.makeText(context as UserConnectionActivity, "Successfully logged in!", Toast.LENGTH_SHORT).show()

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
}