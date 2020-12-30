package com.jaa.likeastarappmpp.connectivity

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectionChecker {
    @SuppressWarnings("deprecation")
    fun checkWifiConnection(context: Context):Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            checkWifiConnectionVersionBeforeM(connectivityManager)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun checkWifiConnectionVersionBeforeM(connectivityManager: ConnectivityManager):Boolean {
        var result = false
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    else -> false
                }
            }
        }
        return result
    }
}