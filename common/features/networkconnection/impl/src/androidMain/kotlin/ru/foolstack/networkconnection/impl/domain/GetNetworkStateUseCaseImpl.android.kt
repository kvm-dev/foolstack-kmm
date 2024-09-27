package ru.foolstack.networkconnection.impl.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.StrictMode
import android.util.Log
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.PlatformContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

actual class GetNetworkStateUseCaseImpl actual constructor(private val platformContext: PlatformContext) : GetNetworkStateUseCase {

      actual override fun isNetworkAvailable(): Boolean {
        return if(isConnectionAvailable(context = platformContext.androidContext)){
            isInternetAvailable()
        }
        else false
    }

    private fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            //mobile internet
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val timeoutMs = 2000
            val sock = Socket()
            val socketAddress: SocketAddress = InetSocketAddress("foolstack.ru", 53)
            sock.connect(socketAddress, timeoutMs)
            sock.close()
            Log.i("CONNECTION STATUS:", "connected")
            true
        } catch (ioException: IOException) {
            Log.i("CONNECTION STATUS:", "disconnected")
            false
        }
    }
}