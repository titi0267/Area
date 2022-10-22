package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(R.layout.fragment_area_main) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<FloatingActionButton>(R.id.profileButton).setOnClickListener {
            (context as AreaActivity).changeFragment(ProfileFragment(), "profile")
        }
        view.findViewById<Button>(R.id.areaListButton).setOnClickListener {
            (context as AreaActivity).changeFragment(AreaListFragment(), "area_list")
        }
        return view
    }
}