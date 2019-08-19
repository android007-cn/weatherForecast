package com.cxyzy.demo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cxyzy.demo.network.response.DailyWeatherResp

data class LocationWeather(var id: String,
                           var isCurrentLocation: Boolean = false,
                           var index: Int = 0,
                           var locationName: String,
                           var weatherList: MutableLiveData<List<DailyWeatherResp.Data>>? = MutableLiveData()
)