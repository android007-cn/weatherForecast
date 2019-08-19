package com.cxyzy.demo.ui.activity

import android.view.View
import androidx.lifecycle.Observer
import com.cxyzy.demo.R
import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.ui.adapter.DailyWeatherAdapter
import com.cxyzy.demo.viewmodels.DailyWeatherViewModel
import com.cxyzy.utils.ext.toast
import kotlinx.android.synthetic.main.activity_repo.*

class DailyWeatherActivity : BaseActivity<DailyWeatherViewModel>() {
    private val adapter = DailyWeatherAdapter()
    override fun viewModel(): DailyWeatherViewModel = DailyWeatherViewModel()

    override fun layoutId(): Int = R.layout.activity_repo

    override fun initViews() {
        rv.adapter = adapter
        adapter.setOnItemClick(this::onItemClick)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
        viewModel().getWeather(
                {
                    progressBar.visibility = View.VISIBLE
                },
                {
                    toast(it.message.toString())
                },
                {
                    progressBar.visibility = View.GONE
                    viewModel().weatherList.observe(this, Observer {
                        adapter.dataList.addAll(it)
                        adapter.notifyDataSetChanged()
                    })
                })
    }

    private fun onItemClick(resp: DailyWeatherResp.Data) {
        viewModel().getRepoDetail(resp.day,
                {
                    progressBar.visibility = View.VISIBLE
                },
                {
                    toast(it.message.toString())
                },
                {
                    progressBar.visibility = View.GONE
                })
    }

    override fun startObserve() {

    }

}
