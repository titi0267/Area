package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.area.R
import com.example.area.activity.AreaActivity

class TopBarFragment : Fragment(R.layout.fragment_top_bar) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<ImageButton>(R.id.top_bar_back_button).setOnClickListener {
            (context as AreaActivity).onBackPressed()
        }
        return view
    }
}