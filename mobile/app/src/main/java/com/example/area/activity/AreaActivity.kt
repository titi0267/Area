package com.example.area.activity

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.area.AREAApplication
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.fragment.area.*
import com.example.area.model.UserInfo
import com.example.area.model.about.About
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Response

class AreaActivity : AppCompatActivity() {

    var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAbout()
        setUserInfo()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LoadingFragment>(R.id.area_fragment_container, "loading_fragment")
            }   
        }
        setContentView(R.layout.activity_area)
        val navbar = findViewById<BottomNavigationView>(R.id.area_bottom_navbar) ?: return
        navbar.setOnItemSelectedListener { item ->
            val fragment = supportFragmentManager.findFragmentByTag("loading_fragment")
            if (fragment != null && fragment.isVisible)
                return@setOnItemSelectedListener(false)
            when (item.itemId) {
                R.id.navbar_home_item -> {
                    changeFragment(AreaListFragment(), "area_list")
                    true
                }
                R.id.navbar_creation_item -> {
                    changeFragment(AreaCreationActionServiceFragment(), "action_service_creation")
                    true
                }
                R.id.navbar_profile_item -> {
                    changeFragment(ProfileFragment(), "profile")
                    true
                }
                else -> false
            }
        }
    }

    fun changeFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.area_fragment_container, fragment, tag)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("area_list")
        if (fragment != null && fragment.isVisible)
            moveTaskToBack(true)
        else
            supportFragmentManager.popBackStack()
    }

    private fun setAbout() {
        val sessionManager = SessionManager(this)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<About>?> =  Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                val about = response.body()!!
                (application as AREAApplication).setAboutClass(about)
                (application as AREAApplication).setAboutBitmapList()
            }
        }
        viewModel.getAboutJson(this, observer)
        viewModel.aboutResponse.observe(this, observer)
    }

    private fun setUserInfo() {
        val sessionManager = SessionManager(this)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val token = sessionManager.fetchAuthToken("user_token") ?: return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<UserInfo>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                (application as AREAApplication).setUserInfoInApp(response.body()!!)
            }
        }
        viewModel.getUserInfo(token, this, observer)
        viewModel.userInfoResponse.observe(this, observer)
    }
}