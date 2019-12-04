package com.prdemo.a829886.projectdemo.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import com.prdemo.a829886.projectdemo.model.State

class AppUtils {
    companion object {
        @SuppressLint("NewApi")
        fun checkNetworkAvailability(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }

        fun removeEmptyItems(state: State) {
            val iterator = state.rows?.iterator()
            while (iterator!!.hasNext()) {
                val stateStructure = iterator.next()
                if (TextUtils.isEmpty(stateStructure.title) && TextUtils.isEmpty(stateStructure.description) && TextUtils.isEmpty(stateStructure.imageHref)) {
                    iterator.remove()
                }
            }
        }
    }
}