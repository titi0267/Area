package com.example.area.fragment.area

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.area.AREAApplication
import com.example.area.R
import com.example.area.activity.AreaActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadingFragment : Fragment(R.layout.fragment_loading) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<Button>(R.id.loading_reload_button).setOnClickListener {
            ((context as AreaActivity).application as AREAApplication).reload = true
            startActivity(Intent(context, AreaActivity::class.java))
        }
        view.findViewById<Button>(R.id.loading_edit_ip_port).setOnClickListener {
            ((context as AreaActivity).application as AREAApplication).reload = false
            (context as AreaActivity).changeFragment(ProfileConnectivityEditFragment(), "change_ip_port")
        }
        GlobalScope.launch {
            waitForSuccess(context ?: return@launch)
        }
        return view
    }
}

private suspend fun waitForSuccess(context: Context) {
    val app = ((context as AreaActivity).application as AREAApplication)
    while ((app.aboutClass == null || app.aboutBitmapList == null || app.userInfo == null) && app.reload == null);
    if (app.reload != null) {
        app.reload = null
        return
    }
    app.inApp = true
    context.changeFragment(AreaListFragment(), "area_list")
}