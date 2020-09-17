package com.tomislav.novacic.weathernovacic.base

import androidx.appcompat.app.AppCompatActivity
import com.tomislav.novacic.weathernovacic.ui.networkUnavailable.NetworkUnavailableActivity
import com.tomislav.novacic.weathernovacic.ui.weather.WeatherActivity
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider.NetworkState
import com.tomislav.novacic.weathernovacic.connectivity.NetworkStateUtil

open class BaseActivity : AppCompatActivity(), ConnectivityStateProvider.ConnectivityStateListener {

    private val stateProvider: ConnectivityStateProvider by lazy {
        ConnectivityStateProvider.createProvider(this)
    }

    override fun onStart() {
        super.onStart()
        stateProvider.addListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        stateProvider.removeListener(this)
    }

    private fun NetworkState.hasInternet(): Boolean {
        return (this as? NetworkState.ConnectedState)?.hasNetworkConnection == true
    }

    override fun onStateChanged(state: NetworkState) {
        val hasInternet = state.hasInternet()
        if (hasInternet != NetworkStateUtil.wasNetworkAvailableBefore) {
            if (hasInternet) {
                startActivity(WeatherActivity.newInstance(applicationContext))
                finish()
            } else {
                startActivity(NetworkUnavailableActivity.newInstance(applicationContext))
                finish()
            }
            NetworkStateUtil.wasNetworkAvailableBefore = hasInternet
        }
    }
}