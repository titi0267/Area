package com.example.area.fragment.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.google.android.material.textview.MaterialTextView

class ProfileUserInfoFragment : Fragment(R.layout.fragment_profile_user_info) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null
        getAndDisplayUserInfo(view)

        return view
    }

    private fun getAndDisplayUserInfo(view: View) {
        val userInfo = ((context as AreaActivity).application as AREAApplication).userInfo ?: return

        view.findViewById<MaterialTextView>(R.id.profile_user_first_name_value).text = userInfo.firstName
        view.findViewById<MaterialTextView>(R.id.profile_user_last_name_value).text = userInfo.lastName
        view.findViewById<MaterialTextView>(R.id.profile_user_email_value).text = userInfo.email
    }
}