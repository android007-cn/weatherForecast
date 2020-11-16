package com.cxyzy.weather.viewmodels

import com.cxyzy.weather.network.response.DailyWeatherResp

data class LocationWeather(
    var id: String,
    var isCurrentLocation: Boolean = false,
    var index: Int = 0,
    var locationName: String,
    var weatherList: List<DailyWeatherResp.Data> = ArrayList()
)