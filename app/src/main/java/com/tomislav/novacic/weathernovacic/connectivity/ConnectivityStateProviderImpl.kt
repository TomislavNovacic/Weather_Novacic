package com.tomislav.novacic.weathernovacic.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider.NetworkState

@RequiresApi(Build.VERSION_CODES.N)
class ConnectivityStateProviderImpl(private val cm: ConnectivityManager) :
    ConnectivityProviderBaseImpl() {

    private val networkCallback = ConnectivityStateCallback()

    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            NetworkState.ConnectedState.Connected(capabilities)
        } else {
            NetworkState.Offline
        }
    }

    private inner class ConnectivityStateCallback : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(NetworkState.ConnectedState.Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NetworkState.Offline)
        }
    }
}