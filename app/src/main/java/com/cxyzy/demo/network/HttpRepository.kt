package com.cxyzy.demo.network

object HttpRepository : BaseHttpRepository() {
    suspend fun getDailyWeather(cityName: String) = api.getDailyWeather(cityName)
}