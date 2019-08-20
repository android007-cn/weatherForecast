package com.cxyzy.demo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_repo.*

class DailyWeatherActivity : AppCompatActivity() {
    private val adapter = DailyWeatherAdapter()
    private val viewModel = DailyWeatherViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        initViews()
        lifecycle.addObserver(viewModel)
    }

    private fun initViews() {
        rv.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
        viewModel.getWeather(
                {
                    progressBar.visibility = View.VISIBLE
                },
                {
                    toast(it.message.toString())
                },
                {
                    progressBar.visibility = View.GONE
                    viewModel.weatherList.observe(this, Observer {
                        adapter.dataList.addAll(it)
                        adapter.notifyDataSetChanged()
                    })
                })
    }

    override fun onDestroy() {
        viewModel.let {
            lifecycle.removeObserver(it)
        }

        super.onDestroy()
    }

}
