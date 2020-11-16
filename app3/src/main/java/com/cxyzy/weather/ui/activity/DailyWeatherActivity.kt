package com.cxyzy.weather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxyzy.weather.R
import com.cxyzy.weather.network.HttpRepository
import com.cxyzy.weather.ui.adapter.DailyWeatherAdapter
import kotlinx.android.synthetic.main.activity_daily_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DailyWeatherActivity : AppCompatActivity() {
    private val adapter = DailyWeatherAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_weather)
        initViews()
    }

    private fun initViews() {
        initRecyclerView()
        queryDailyWeather()
        initSwipeRefreshLayout()
    }

    private fun initRecyclerView() {
        rv.adapter = adapter
    }

    private fun queryDailyWeather() {
        GlobalScope.launch(Dispatchers.Main)
        {
            adapter.dataList.clear()
            adapter.dataList.addAll(HttpRepository.getDailyWeather().dataList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            queryDailyWeather()
        }
    }

}
