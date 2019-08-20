package com.cxyzy.demo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxyzy.demo.R
import com.cxyzy.demo.network.HttpRepository
import com.cxyzy.demo.ui.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initViews()
    }

    private fun initViews() {
        val adapter = WeatherAdapter()
        rv.adapter = adapter

        GlobalScope.launch(Dispatchers.Main)
        {
            adapter.dataList.addAll(HttpRepository.getDailyWeather().dataList)
            adapter.notifyDataSetChanged()
        }
    }

}
