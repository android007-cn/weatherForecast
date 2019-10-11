package com.cxyzy.weather.ext

import com.cxyzy.weather.network.interceptor.HttpLogInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                addInterceptor(HttpLogInterceptor())
            }.build()
}