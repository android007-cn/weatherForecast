package com.cxyzy.demo.network

object HttpRepository : BaseHttpRepository() {
    suspend fun getDailyWeather(cityName: String) = networkApi.getDailyWeather(cityName)
    suspend fun getRealTimeWeather(cityName: String) = networkApi.getRealTimeWeather(cityName)
}