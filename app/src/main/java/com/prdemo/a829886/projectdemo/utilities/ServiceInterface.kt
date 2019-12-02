package com.prdemo.a829886.projectdemo.utilities

import com.prdemo.a829886.projectdemo.model.BioGraphicData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interface for calling the respective end point service
 */
interface ServiceInterface {
    @GET("s/2iodh4vg0eortkl/facts.js")
    fun getHeroes(): Call<BioGraphicData>
}