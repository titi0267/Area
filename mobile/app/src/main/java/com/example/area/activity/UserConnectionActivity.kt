package com.example.area.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.area.R
import com.example.area.fragment.user.ConnectivityFragment
import com.example.area.fragment.user.LoginFragment
import com.example.area.utils.SessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserConnectionActivity : AppCompatActivity() {

    var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        setContentView(R.layout.activity_user_connection)
        if (savedInstanceState == null) {
            if (sessionManager.fetchAuthToken("url") == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<ConnectivityFragment>(R.id.user_connection_fragment_container, "connectivity")
                }
            }
            else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<LoginFragment>(R.id.user_connection_fragment_container, "login")
                }
            }
        }
        findViewById<FloatingActionButton>(R.id.user_connection_connection_settings).setOnClickListener {
            changeFragment(ConnectivityFragment(), "connectivity")
        }
    }

    fun changeFragment(fragment: Fragment, tag: String?) {
        if (tag != null && tag != "connectivity")
            setVisibilityFAB(View.VISIBLE)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.user_connection_fragment_container, fragment, tag)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        if (loading)
            return
        val sessionManager = SessionManager(this)
        val fragment = supportFragmentManager.findFragmentByTag("not_logged_in")
        if ((fragment != null && fragment.isVisible) || (sessionManager.fetchAuthToken("url") == null))
            moveTaskToBack(true)
        else {
            supportFragmentManager.popBackStack()
            setVisibilityFAB(View.VISIBLE)
        }
    }

    fun setVisibilityFAB(value: Int) {
        findViewById<FloatingActionButton>(R.id.user_connection_connection_settings).visibility = value
    }
}