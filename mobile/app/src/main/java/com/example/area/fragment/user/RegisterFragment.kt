package com.example.area.fragment.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.area.activity.MainActivity
import com.example.area.activity.UserConnectionActivity
import com.example.area.model.ErrorResponse
import com.example.area.model.RegisterFields
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

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
        view.findViewById<Button>(R.id.register_redirect_to_login_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            (context as UserConnectionActivity).changeFragment(LoginFragment(), "login")
        }
        button.setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            //Store register fields
            val registerForm = RegisterFields(
                view.findViewById<EditText>(R.id.register_first_name_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_last_name_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_email_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.register_password_field_edit_text).text.toString()
            )
            // Check registration validity
            try {
                checkRegisterField(registerForm)
                checkConfirmPassword(registerForm.password, view.findViewById<EditText>(R.id.register_confirm_password_field_edit_text).text.toString())
            }
            catch (e: IllegalArgumentException) {
                Toast.makeText(context as UserConnectionActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerRequest(registerForm)
        }

        return view
    }

    private fun registerRequest(registerForm: RegisterFields) {
        var token: String?
        val sessionManager = SessionManager(context as UserConnectionActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val observer = Observer<Response<Token>?> { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    sessionManager.saveAuthToken("user_token", token!!)
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
            else {
                val error: ErrorResponse = Gson().fromJson((response.errorBody() ?: return@Observer).charStream(), (object : TypeToken<ErrorResponse>() {}.type)) ?: return@Observer
                Toast.makeText(context as UserConnectionActivity, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.register(registerForm, context as UserConnectionActivity, observer)
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun registerFocusListener(view: View)
    {
        textFieldsFocusListener(view, R.id.register_first_name_field_edit_text, R.id.register_first_name_field_layout, ::checkNames)
        textFieldsFocusListener(view, R.id.register_last_name_field_edit_text, R.id.register_last_name_field_layout, ::checkNames)
        textFieldsFocusListener(view, R.id.register_email_field_edit_text, R.id.register_email_field_layout, ::checkEmail)
        textFieldsFocusListener(view, R.id.register_password_field_edit_text, R.id.register_password_field_layout, ::checkPasswordRegister)
        confirmPasswordFocusListener(view, R.id.register_password_field_edit_text, R.id.register_confirm_password_field_edit_text, R.id.register_confirm_password_field_layout)
    }

    private fun confirmPasswordFocusListener(view: View, idEditTextPassword: Int, idEditTextConfirmPassword: Int, idTextLayoutConfirmPassword: Int) {
        val textLayoutConfirm = view.findViewById<TextInputLayout>(idTextLayoutConfirmPassword)
        val editTextPassword = view.findViewById<TextInputEditText>(idEditTextPassword)
        val editTextConfirm = view.findViewById<TextInputEditText>(idEditTextConfirmPassword)

        editTextPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                try {
                    checkConfirmPassword(editTextPassword.text.toString(), editTextConfirm.text.toString())
                } catch (e: IllegalArgumentException) {
                    textLayoutConfirm.error = e.message
                    return@setOnFocusChangeListener
                }
            }
            textLayoutConfirm.error = null
        }
        editTextConfirm.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                try {
                    checkConfirmPassword(editTextPassword.text.toString(), editTextConfirm.text.toString())
                } catch (e: IllegalArgumentException) {
                    textLayoutConfirm.error = e.message
                    return@setOnFocusChangeListener
                }
            }
            textLayoutConfirm.error = null
        }
    }
}