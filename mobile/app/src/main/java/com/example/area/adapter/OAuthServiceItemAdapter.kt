package com.example.area.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.area.MainViewModel
import com.example.area.MainViewModelFactory
import com.example.area.R
import com.example.area.activity.AreaActivity
import com.example.area.activity.OAuthLinkingActivity
import com.example.area.model.OAuthServiceListElement
import com.example.area.repository.Repository
import com.example.area.utils.SessionManager
import com.google.android.material.textview.MaterialTextView
import retrofit2.Response

class OAuthServiceItemAdapter(private val context: Context, private val dataset: List<OAuthServiceListElement>) : RecyclerView.Adapter<OAuthServiceItemAdapter.OAuthServiceViewHolder>() {
    private var selectedItem = 0

    class OAuthServiceViewHolder(private val view: View): RecyclerView.ViewHolder(view);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OAuthServiceViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.oauth_service_item, parent, false)
        val button: Button = adapterLayout.findViewById<Button>(R.id.oauth_service_button)
        val index = selectedItem

        if (selectedItem == itemCount)
            selectedItem = 0
        adapterLayout.findViewById<ImageView>(R.id.oauth_service_logo).setImageDrawable(BitmapDrawable(context.resources, dataset[index].imageBitmap))
        adapterLayout.findViewById<MaterialTextView>(R.id.oauth_service_name).text = dataset[index].name
        if (dataset[index].connected) {
            button.text = context.resources.getString(R.string.oauth_service_item_button_true)
            button.setTextColor(context.resources.getColor(R.color.green))
            button.setOnClickListener {
                //UNLINK
            }
        } else {
            button.text = context.resources.getString(R.string.oauth_service_item_button_false)
            button.setTextColor(context.resources.getColor(R.color.red))
            button.setOnClickListener {
                getOAuthLinkRequest(dataset[index].oauthName, context)
            }
        }
        selectedItem++
        return OAuthServiceViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: OAuthServiceViewHolder, position: Int) {}

    override fun getItemCount() = dataset.size

    private fun getOAuthLinkRequest(service: String, context: Context) {
        val sessionManager = SessionManager(context as AreaActivity)
        val url = sessionManager.fetchAuthToken("url") ?: return
        val token = sessionManager.fetchAuthToken("user_token") ?:return
        val rep = Repository(url)
        val viewModelFactory = MainViewModelFactory(rep)
        val viewModel = ViewModelProvider(context, viewModelFactory)[MainViewModel::class.java]
        val observer: Observer<Response<String>?> = Observer { response ->
            if (response == null)
                return@Observer
            if (response.isSuccessful) {
                val oAuthLink = response.body()!!.toString()
                val bundle = Bundle()
                val intent = Intent(context as AreaActivity, OAuthLinkingActivity::class.java)
                bundle.putString("link", oAuthLink)
                bundle.putString("service", service)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }

        viewModel.getServiceLink(token, service, context, observer)
        viewModel.linkResponse.observe(context, observer)
    }
}