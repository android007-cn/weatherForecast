package com.cxyzy.weather.network

import com.cxyzy.weather.ext.KoinInject
import com.cxyzy.weather.utils.OkHttpUrl.BASE_WEATHER_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseHttpRepository {
    val weatherApi: NetworkApi by lazy {
        val okHttpClient = KoinInject.getFromKoin<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkApi::class.java)
    }

    val cityNameApi: NetworkApi by lazy {
        val okHttpClient = KoinInject.getFromKoin<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkApi::class.java)
    }
}