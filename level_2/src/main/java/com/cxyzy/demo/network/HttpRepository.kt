package com.cxyzy.demo.network

object HttpRepository : BaseHttpRepository() {
    suspend fun getDailyWeather() = api.getDailyWeather(cityName = "南京")
}