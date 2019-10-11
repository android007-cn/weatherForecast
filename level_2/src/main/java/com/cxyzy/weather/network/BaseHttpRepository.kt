package com.cxyzy.weather.network

import com.cxyzy.weather.ext.provideOkHttpClient
import com.cxyzy.weather.utils.OkHttpUrl.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseHttpRepository {
    val networkApi: NetworkApi by lazy {
        val okHttpClient = provideOkHttpClient()
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(NetworkApi::class.java)
    }
}