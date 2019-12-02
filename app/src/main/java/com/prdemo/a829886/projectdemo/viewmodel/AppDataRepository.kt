package com.prdemo.a829886.projectdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prdemo.a829886.projectdemo.utilities.AppConstants
import com.prdemo.a829886.projectdemo.utilities.ServiceInterface
import com.prdemo.a829886.projectdemo.model.BioGraphicData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDataRepository {

    private val liveData : MutableLiveData<BioGraphicData> = MutableLiveData()

    fun getData() : LiveData<BioGraphicData> {
        loadHeroes()
        return liveData
    }

    private fun loadHeroes() {
        val retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(ServiceInterface::class.java)
        val call = api.getHeroes()

        call.enqueue(object : Callback<BioGraphicData> {
            override fun onFailure(call: Call<BioGraphicData>, t: Throwable) {
            }

            override fun onResponse(call: Call<BioGraphicData>, response: retrofit2.Response<BioGraphicData>) {

                val bioGraphicData : BioGraphicData? = response.body()
                liveData.value = bioGraphicData
            }

        })
    }
}