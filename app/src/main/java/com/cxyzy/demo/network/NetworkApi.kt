package com.cxyzy.demo.network

import com.cxyzy.demo.network.response.DailyWeatherResp
import com.cxyzy.demo.network.response.RealTimeWeatherResp
import com.cxyzy.demo.utils.ApiToken.APP_ID
import com.cxyzy.demo.utils.ApiToken.APP_SECRET
import com.cxyzy.demo.utils.ApiToken.CURRENT_API_VER
import com.cxyzy.demo.utils.ApiToken.DAILY_API_VER
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("?appid=$APP_ID&appsecret=$APP_SECRET&version=$DAILY_API_VER")
    suspend fun queryDailyWeather(@Query("city") cityName: String): DailyWeatherResp

    @GET("?appid=$APP_ID&appsecret=$APP_SECRET&version=$CURRENT_API_VER")
    suspend fun queryRealTimeWeather(@Query("city") cityName: String): RealTimeWeatherResp
}