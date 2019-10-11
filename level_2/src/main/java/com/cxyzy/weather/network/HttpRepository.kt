package com.cxyzy.weather.network

object HttpRepository : BaseHttpRepository() {
    suspend fun getDailyWeather() = networkApi.getDailyWeather(cityName = "南京")
}