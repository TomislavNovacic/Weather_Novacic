package com.tomislav.novacic.weathernovacic.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NETWORK_INFO
import android.net.NetworkInfo
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider.NetworkState

@Suppress("DEPRECATION")
class ConnectivityStateProviderLegacyImpl(
    private val context: Context,
    private val cm: ConnectivityManager
) : ConnectivityProviderBaseImpl() {

    private val receiver = ConnectivityStateReceiver()

    override fun subscribe() {
        context.registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun unsubscribe() {
        context.unregisterReceiver(receiver)
    }

    override fun getNetworkState(): NetworkState {
        val activeNetworkInfo = cm.activeNetworkInfo
        return if (activeNetworkInfo != null) {
            NetworkState.ConnectedState.ConnectedLegacy(activeNetworkInfo)
        } else {
            NetworkState.Offline
        }
    }

    private inner class ConnectivityStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val networkInfo = cm.activeNetworkInfo
            val fallbackNetworkInfo: NetworkInfo? = intent.getParcelableExtra(EXTRA_NETWORK_INFO)
            val state: NetworkState =
                if (networkInfo?.isConnectedOrConnecting == true) {
                    NetworkState.ConnectedState.ConnectedLegacy(networkInfo)
                } else if (networkInfo != null && fallbackNetworkInfo != null && networkInfo.isConnectedOrConnecting != fallbackNetworkInfo.isConnectedOrConnecting) {
                    NetworkState.ConnectedState.ConnectedLegacy(fallbackNetworkInfo)
                } else {
                    val state = networkInfo ?: fallbackNetworkInfo
                    if (state != null) NetworkState.ConnectedState.ConnectedLegacy(state) else NetworkState.Offline
                }
            dispatchChange(state)
        }
    }
}