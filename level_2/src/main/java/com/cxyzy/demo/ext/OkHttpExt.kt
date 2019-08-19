package com.cxyzy.demo.ext

import com.cxyzy.demo.network.interceptor.HttpLogInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                addInterceptor(HttpLogInterceptor())
            }.build()
}