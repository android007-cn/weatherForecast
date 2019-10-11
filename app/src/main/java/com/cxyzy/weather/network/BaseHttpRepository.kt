package com.cxyzy.weather.network

import com.cxyzy.weather.ext.KoinInject
import com.cxyzy.weather.utils.OkHttpUrl.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseHttpRepository {
    val networkApi: NetworkApi by lazy {
        val okHttpClient = KoinInject.getFromKoin<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkApi::class.java)
    }
}