package com.cxyzy.weather.network

import com.cxyzy.weather.network.response.DailyWeatherResp
import com.cxyzy.weather.network.response.RealTimeWeatherResp
import com.cxyzy.weather.utils.ApiToken.APP_ID
import com.cxyzy.weather.utils.ApiToken.APP_SECRET
import com.cxyzy.weather.utils.ApiToken.CURRENT_API_VER
import com.cxyzy.weather.utils.ApiToken.DAILY_API_VER
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("?appid=$APP_ID&appsecret=$APP_SECRET&version=$DAILY_API_VER")
    suspend fun queryDailyWeather(@Query("city") cityName: String): DailyWeatherResp

    @GET("?appid=$APP_ID&appsecret=$APP_SECRET&version=$CURRENT_API_VER")
    suspend fun queryRealTimeWeather(@Query("city") cityName: String): RealTimeWeatherResp
}