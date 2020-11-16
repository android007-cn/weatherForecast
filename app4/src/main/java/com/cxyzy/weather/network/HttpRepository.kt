package com.cxyzy.weather.network

object HttpRepository : BaseHttpRepository() {
    suspend fun queryDailyWeather(cityName: String) = weatherApi.queryDailyWeather(cityName)
    suspend fun getRealTimeWeather(cityName: String) = weatherApi.queryRealTimeWeather(cityName)
}