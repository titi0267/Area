package com.example.area.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.area.R
import com.example.area.fragment.user.NotLoggedInFragment

class UserConnectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NotLoggedInFragment>(R.id.user_connection_fragment_container, "not_logged_in")
            }
        }
        setContentView(R.layout.activity_user_connection)
    }

    fun changeFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.user_connection_fragment_container, fragment, tag)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("not_logged_in")
        if (fragment != null && fragment.isVisible)
            moveTaskToBack(true)
        else
            supportFragmentManager.popBackStack()
    }
}