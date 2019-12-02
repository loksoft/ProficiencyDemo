package com.prdemo.a829886.projectdemo.utilities

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prdemo.a829886.projectdemo.model.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This will make service call and parse either of error or success response
 */

class AppDataRepository {
    private val baseUrl = "https://dl.dropboxusercontent.com/"
    private val liveData: MutableLiveData<State> = MutableLiveData()
    var serverCallListener: ServerCallListener? = null

    fun getData(serverCallListener: ServerCallListener): LiveData<State> {
        this.serverCallListener = serverCallListener
        loadData()
        return liveData
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(ServiceInterface::class.java)
        val call = api.getStateDemoGraphicData()

        call.enqueue(object : Callback<State> {
            override fun onFailure(call: Call<State>, t: Throwable) {
                if (serverCallListener != null) {
                    serverCallListener?.onErrorTriggered()
                }
            }

            override fun onResponse(call: Call<State>, response: retrofit2.Response<State>) {
                val state: State? = response.body()
                val iterator = state?.rows?.iterator()
                while (iterator!!.hasNext()) {
                    val stateStructure = iterator.next()
                    if (TextUtils.isEmpty(stateStructure.title) && TextUtils.isEmpty(stateStructure.description) && TextUtils.isEmpty(stateStructure.imageHref)) {
                        iterator.remove()
                    }
                }
                liveData.value = state
            }
        })
    }

    interface ServerCallListener {
        fun onErrorTriggered()
    }
}