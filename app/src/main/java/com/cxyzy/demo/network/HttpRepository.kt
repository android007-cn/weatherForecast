package com.cxyzy.demo.network

object HttpRepository : BaseHttpRepository() {
    suspend fun queryDailyWeather(cityName: String) = networkApi.queryDailyWeather(cityName)
    suspend fun getRealTimeWeather(cityName: String) = networkApi.queryRealTimeWeather(cityName)
}