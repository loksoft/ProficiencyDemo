package com.prdemo.a829886.projectdemo.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prdemo.a829886.projectdemo.model.State
import com.prdemo.a829886.projectdemo.utilities.AppUtils
import com.prdemo.a829886.projectdemo.utilities.ServiceInterface
import retrofit2.Call
import retrofit2.Callback

/**
 * This will make service call and parse either of error or success response
 */

class AppDataRepository {
    private val api = AppUtils.getRetrofitInstance()?.create(ServiceInterface::class.java)
    private val liveData: MutableLiveData<State> = MutableLiveData()
    var serverCallListener: ServerCallListener? = null
    private var context: Context? = null

    fun getData(serverCallListener: ServerCallListener, context: Context): LiveData<State> {
        this.serverCallListener = serverCallListener
        this.context = context
        loadData()
        return liveData
    }

    private fun loadData() {
        if (AppUtils.checkNetworkAvailability(context)) {
            val call = api?.getStateDemoGraphicData()
            call?.enqueue(object : Callback<State> {
                override fun onFailure(call: Call<State>, t: Throwable) {
                    if (serverCallListener != null) {
                        serverCallListener?.onErrorTriggered()
                    }
                }

                override fun onResponse(call: Call<State>, response: retrofit2.Response<State>) {
                    val state: State? = response.body()
                    AppUtils.removeEmptyItems(state)
                    liveData.value = state
                }
            })
        } else {
            serverCallListener?.onNetworkErrorTriggered()
        }
    }

    interface ServerCallListener {
        fun onErrorTriggered()
        fun onNetworkErrorTriggered()
    }
}