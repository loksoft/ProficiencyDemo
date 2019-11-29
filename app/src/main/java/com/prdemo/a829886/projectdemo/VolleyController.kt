package com.prdemo.a829886.projectdemo

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object VolleyController {
   private var requestQueue : RequestQueue? = null

    fun getInstance(context: Context) : RequestQueue {
        requestQueue = Volley.newRequestQueue(context)
        return requestQueue!!
    }
}