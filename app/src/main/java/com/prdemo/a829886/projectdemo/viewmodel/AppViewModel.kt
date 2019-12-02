package com.prdemo.a829886.projectdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.prdemo.a829886.projectdemo.model.State
import com.prdemo.a829886.projectdemo.utilities.AppDataRepository


class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataRepository = AppDataRepository()

    fun getData(serverCallListener: AppDataRepository.ServerCallListener): LiveData<State> {
        return appDataRepository.getData(serverCallListener)
    }
}