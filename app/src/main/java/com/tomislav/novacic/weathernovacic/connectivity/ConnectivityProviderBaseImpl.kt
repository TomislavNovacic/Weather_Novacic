package com.tomislav.novacic.weathernovacic.connectivity

import android.os.Handler
import android.os.Looper
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider.ConnectivityStateListener
import com.tomislav.novacic.weathernovacic.connectivity.ConnectivityStateProvider.NetworkState

abstract class ConnectivityProviderBaseImpl : ConnectivityStateProvider {
    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<ConnectivityStateListener>()
    private var subscribed = false

    override fun addListener(listener: ConnectivityStateListener) {
        listeners.add(listener)
        listener.onStateChanged(getNetworkState())
        verifySubscription()
    }

    override fun removeListener(listener: ConnectivityStateListener) {
        listeners.remove(listener)
        verifySubscription()
    }

    private fun verifySubscription() {
        if (!subscribed && listeners.isNotEmpty()) {
            subscribe()
            subscribed = true
        } else if (subscribed && listeners.isEmpty()) {
            unsubscribe()
            subscribed = false
        }
    }

    protected fun dispatchChange(state: NetworkState) {
        handler.post {
            for (listener in listeners) {
                listener.onStateChanged(state)
            }
        }
    }

    protected abstract fun subscribe()
    protected abstract fun unsubscribe()
}