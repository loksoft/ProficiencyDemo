package com.prdemo.a829886.projectdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.prdemo.a829886.projectdemo.model.AppDataRepository
import com.prdemo.a829886.projectdemo.model.State

/**
 * ViewModel for app and it provides live data to the view {@MainActivity}
 */

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataRepository = AppDataRepository()
    fun getLiveData(serverCallListener: AppDataRepository.ServerCallListener): LiveData<State> {
        return appDataRepository.getData(serverCallListener, getApplication())
    }
}