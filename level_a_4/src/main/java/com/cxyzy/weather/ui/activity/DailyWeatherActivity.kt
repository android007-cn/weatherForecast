package com.cxyzy.weather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cxyzy.weather.R
import com.cxyzy.weather.ui.adapter.DailyWeatherAdapter
import com.cxyzy.weather.viewmodels.DailyWeatherViewModel
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

        viewModel.getWeather {
            viewModel.weatherList.observe(this, Observer {
                adapter.dataList.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }
    }

    override fun onDestroy() {
        viewModel.let {
            lifecycle.removeObserver(it)
        }

        super.onDestroy()
    }

}
