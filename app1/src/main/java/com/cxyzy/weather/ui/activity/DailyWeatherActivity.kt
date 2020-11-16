package com.cxyzy.weather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxyzy.weather.R
import com.cxyzy.weather.network.HttpRepository
import kotlinx.android.synthetic.main.activity_daily_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DailyWeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_weather)
        initViews()
    }

    private fun initViews() {
        queryDailyWeather()
    }

    private fun queryDailyWeather() {
        GlobalScope.launch(Dispatchers.Main)
        {
            textView.text = HttpRepository.getDailyWeather().dataList.toString()
        }
    }

}
