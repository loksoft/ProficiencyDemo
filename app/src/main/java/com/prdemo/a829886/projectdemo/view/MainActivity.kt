package com.prdemo.a829886.projectdemo.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.prdemo.a829886.projectdemo.R
import com.prdemo.a829886.projectdemo.adapters.StateAdapter
import com.prdemo.a829886.projectdemo.databinding.ActivityMainBinding
import com.prdemo.a829886.projectdemo.model.BioGraphicData
import com.prdemo.a829886.projectdemo.viewmodel.AppViewModel


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, Observer<BioGraphicData> {

    private var recyclerView: RecyclerView? = null
    private var actionBar: ActionBar? = null
    private var refreshPage: SwipeRefreshLayout? = null
    private var appViewModel: AppViewModel? = null
    private var activityMainBinding: ActivityMainBinding ? = null

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
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        appViewModel?.getHeroes()?.observe(this, this)
    }

    /**
     * This method will provide live data
     */
    override fun onChanged(bioGraphicData: BioGraphicData?) {
        activityMainBinding?.visibleStatus = View.GONE
        refreshPage?.isRefreshing = false
        actionBar?.title = bioGraphicData?.title
        val stateAdapter = StateAdapter(this@MainActivity, bioGraphicData!!)
        recyclerView?.adapter = stateAdapter
    }

    override fun onRefresh() {
        appViewModel?.getHeroes()?.observe(this, this)
    }
}
