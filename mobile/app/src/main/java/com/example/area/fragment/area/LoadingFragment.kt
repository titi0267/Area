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
import com.example.area.activity.UserConnectionActivity
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
            startActivity(Intent(context, AreaActivity::class.java))
        }
        GlobalScope.launch {
            waitForSuccess(context ?: return@launch)
        }
        return view
    }
}

private suspend fun waitForSuccess(context: Context) {
    val app = ((context as AreaActivity).application as AREAApplication)
    while (app.aboutClass == null || app.aboutBitmapList == null || app.userInfo == null);
    context.changeFragment(MainFragment(), "main_fragment")
}