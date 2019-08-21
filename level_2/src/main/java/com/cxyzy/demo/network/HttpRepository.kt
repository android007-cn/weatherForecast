package com.cxyzy.demo.network

object HttpRepository : BaseHttpRepository() {
    suspend fun getDailyWeather() = networkApi.getDailyWeather(cityName = "南京")
}