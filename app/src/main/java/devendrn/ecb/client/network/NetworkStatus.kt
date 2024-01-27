package devendrn.ecb.client.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class NetworkStatus(
    private val context: Context
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isLoggedIn: Flow<Boolean> = flow {
        emit(false)
    }

    val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(true) }
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(false) }
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    val activityUrl: Flow<String?> = flow {
        emit(null)
    }
}