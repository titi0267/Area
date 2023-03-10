package com.example.area.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.*
import com.example.area.fragment.area.OAuthLinkingFragment
import com.example.area.model.ErrorResponse
import com.example.area.model.LoginFields
import com.example.area.model.Token
import com.example.area.repository.Repository
import com.example.area.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        loginFocusListener(view)
        view.findViewById<Button>(R.id.login_redirect_to_register_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            (context as UserConnectionActivity).changeFragment(RegisterFragment(), "register")
        }
        view.findViewById<Button>(R.id.login_with_google_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            getOAuthLinkRequestThenOAuth()
        }
        view.findViewById<Button>(R.id.request_button).setOnClickListener {
            if ((context as UserConnectionActivity).loading)
                return@setOnClickListener
            // Store email and password
            val loginForm = LoginFields(
                view.findViewById<EditText>(R.id.login_email_field_edit_text).text.toString(),
                view.findViewById<EditText>(R.id.login_password_field_edit_text).text.toString(),
            )
            // Login error handling
            try {
                checkLoginField(loginForm)
            }
            catch (e: IllegalArgumentException) {
                Toast.makeText(context as UserConnectionActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginRequest(loginForm)
        }
        return view
    }

    private fun loginRequest(loginForm: LoginFields)
    {
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
        // Login post request
        viewModel.login(loginForm, context as UserConnectionActivity, observer)
        // Receive login response
        viewModel.userResponse.observe(viewLifecycleOwner, observer)
    }

    private fun loginFocusListener(view: View)
    {
        textFieldsFocusListener(view, R.id.login_email_field_edit_text, R.id.login_email_field_layout, ::checkEmail)
        textFieldsFocusListener(view, R.id.login_password_field_edit_text, R.id.login_password_field_layout, ::checkPasswordLogin)
    }

    private fun getOAuthLinkRequestThenOAuth() {
        val sessionManager = SessionManager(context as UserConnectionActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val observer: Observer<Response<String>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                val oAuthLink = response.body()!!.toString()
                val bundle = Bundle()
                val intent = Intent(context as UserConnectionActivity, OAuthLoginWithGoogleActivity::class.java)
                bundle.putString("link", oAuthLink)
                intent.putExtras(bundle)
                GlobalScope.launch {
                    waitForSuccess(context as UserConnectionActivity)
                }
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getGoogleRegisterLink(context as UserConnectionActivity, observer)
        viewModel.linkResponse.observe(context as UserConnectionActivity, observer)
    }

    private suspend fun waitForSuccess(context:  UserConnectionActivity) {
        while (((context).application as AREAApplication).successOauth == null);
        if (((context).application as AREAApplication).successOauth == true) {
            onSuccessOauth(context)
        }
        else {
            onFailureOauth(context)
        }
        ((context).application as AREAApplication).successOauth = null;
    }

    private fun onSuccessOauth(context: Context) {
        val fragment: LoginFragment = ((context as UserConnectionActivity).supportFragmentManager.findFragmentByTag("login")?: return) as LoginFragment

        while (!fragment.isStateSaved);
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Successfully logged in!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private fun onFailureOauth(context: Context) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context as UserConnectionActivity, "OAuth failure!", Toast.LENGTH_LONG).show()
        }
    }
}