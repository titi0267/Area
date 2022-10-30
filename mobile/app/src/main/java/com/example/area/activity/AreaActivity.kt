package com.example.area.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.area.R
import com.example.area.fragment.area.MainFragment
import com.example.area.fragment.area.NewRequestTestFragment

class AreaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.area_fragment_container, "main_fragment")
            }   
        }
        setContentView(R.layout.activity_area)
    }

    fun changeFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.area_fragment_container, fragment, tag)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("main_fragment")
        if (fragment != null && fragment.isVisible)
            moveTaskToBack(true)
        else
            supportFragmentManager.popBackStack()
    }
}