package com.prdemo.a829886.projectdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.prdemo.a829886.projectdemo.model.BioGraphicData


class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataRepository = AppDataRepository()

    fun getHeroes(): LiveData<BioGraphicData> {
        return appDataRepository.getData()
    }
}