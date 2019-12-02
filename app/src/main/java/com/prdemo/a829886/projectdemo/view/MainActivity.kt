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

    private lateinit var recyclerView: RecyclerView
    private var actionBar: ActionBar? = null
    private lateinit var refreshPage: SwipeRefreshLayout
    private lateinit var appViewModel: AppViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initResources()
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        appViewModel.getHeroes().observe(this, this)
    }

    private fun initResources() {
        actionBar = supportActionBar
        recyclerView = activityMainBinding.mainList
        refreshPage = activityMainBinding.refreshPage
        refreshPage.setOnRefreshListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        activityMainBinding.visibleStatus = View.VISIBLE
    }

    /**
     * This method will provide live data with respect to activity scope
     */
    override fun onChanged(bioGraphicData: BioGraphicData?) {
        activityMainBinding.visibleStatus = View.GONE
        refreshPage.isRefreshing = false
        actionBar?.title = bioGraphicData?.title
        val stateAdapter = StateAdapter(bioGraphicData!!)
        recyclerView.adapter = stateAdapter
    }

    override fun onRefresh() {
        appViewModel.getHeroes().observe(this, this)
    }
}
