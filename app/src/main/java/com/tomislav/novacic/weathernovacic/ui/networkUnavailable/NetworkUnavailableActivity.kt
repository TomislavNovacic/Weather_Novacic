package com.tomislav.novacic.weathernovacic.ui.networkUnavailable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tomislav.novacic.weathernovacic.R
import com.tomislav.novacic.weathernovacic.base.BaseActivity

class NetworkUnavailableActivity : BaseActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, NetworkUnavailableActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_unavailable)
    }
}