package com.example.flowersapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class InternetUtils {

    companion object {
        fun isInternetConnection(context: Context?): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        } else {
                            @Suppress("DEPRECATION") val networkInfo =
                                connectivityManager.activeNetworkInfo ?: return false
                            @Suppress("DEPRECATION")
                            return networkInfo.isConnected
                        }
                    } else {
                        @Suppress("DEPRECATION") val networkInfo =
                            connectivityManager.activeNetworkInfo ?: return false
                        @Suppress("DEPRECATION")
                        return networkInfo.isConnected
                    }
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                            return true
                        }
                    }
                }
            }
            return false
        }
    }
}