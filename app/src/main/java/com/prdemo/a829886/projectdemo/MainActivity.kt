package com.prdemo.a829886.projectdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.prdemo.a829886.projectdemo.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        fetchJsonData()
    }

    private var recyclerView: RecyclerView? = null
    private var actionBar: ActionBar? = null
    private var refreshPage: SwipeRefreshLayout? = null
    private var requestQueue: RequestQueue? = null
    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        actionBar = supportActionBar
        recyclerView = activityMainBinding?.mainList
        refreshPage = activityMainBinding?.refreshPage
        refreshPage?.setOnRefreshListener(this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        activityMainBinding?.visibleStatus = View.VISIBLE
        requestQueue = VolleyController.getInstance(this)
        fetchJsonData()
    }


    private fun fetchJsonData() {
        val jsonObjectRequest = object : JsonObjectRequest(AppConstants.BASE_URL, null, Response.Listener {
            activityMainBinding?.visibleStatus = View.GONE
            refreshPage?.isRefreshing = false
            val stateList = ArrayList<States>()
            val title = it.getString(AppConstants.TITLE_KEY)
            val jsonArray = it.getJSONArray(AppConstants.ROWS_KEY)
            for (position in 0 until jsonArray.length()) {
                val stateJsonObject = jsonArray.get(position) as JSONObject
                val states = States()
                states.title = stateJsonObject.getString(AppConstants.TITLE_KEY)
                states.description = stateJsonObject.getString(AppConstants.DESCRIPTION_KEY)
                states.imageHref = stateJsonObject.getString(AppConstants.IMAGE_URL_KEY)
                stateList.add(states)
            }
            actionBar?.title = title
            val stateAdapter = StateAdapter(this@MainActivity, stateList)
            recyclerView?.adapter = stateAdapter

        }, Response.ErrorListener { error ->
            activityMainBinding?.visibleStatus = View.GONE
            refreshPage?.isRefreshing = false
            Toast.makeText(this@MainActivity, "Something went wrong...Please try again later $error", Toast.LENGTH_SHORT).show()
        }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
                try {
                    var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
                    if (cacheEntry == null) {
                        cacheEntry = Cache.Entry()
                    }
                    val cacheHitButRefreshed = (3 * 60 * 1000).toLong()
                    val cacheExpired = (24 * 60 * 60 * 1000).toLong()
                    val now = System.currentTimeMillis()
                    val softExpire = now + cacheHitButRefreshed
                    val ttl = now + cacheExpired
                    cacheEntry.data = response.data
                    cacheEntry.softTtl = softExpire
                    cacheEntry.ttl = ttl
                    var headerValue: String? = response.headers[AppConstants.DATE_KEY]
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
                    }
                    headerValue = response.headers[AppConstants.LAST_MODIFIED_KEY]
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
                    }
                    cacheEntry.responseHeaders = response.headers
                    val jsonString = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers)))
                    return Response.success(JSONObject(jsonString), cacheEntry)
                } catch (e: UnsupportedEncodingException) {
                    return Response.error(ParseError(e))
                } catch (e: JSONException) {
                    return Response.error(ParseError(e))
                }
            }
        }
        requestQueue?.add(jsonObjectRequest)
    }
}
