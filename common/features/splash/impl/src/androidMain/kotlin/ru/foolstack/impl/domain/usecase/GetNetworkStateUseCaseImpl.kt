package ru.foolstack.impl.domain.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import ru.foolstack.splash.api.domain.usecase.GetNetworkStateUseCase
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


class GetNetworkStateUseCaseImpl(private val context: Context):GetNetworkStateUseCase {

     private fun isConnectionAvailable(): Boolean {
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

    private fun isInternetAvailable(context: Context?): Boolean {
        return try {
            val policy = ThreadPolicy.Builder()
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

    override fun isNetworkAvailable(): Boolean {
       return if(isConnectionAvailable()){
           isInternetAvailable(context)
        }
        else false
    }
}