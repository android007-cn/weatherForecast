package com.cxyzy.weather.ext

import com.cxyzy.weather.network.interceptor.HttpLogInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun provideOkHttpClient(): OkHttpClient {
//    val sslParams = HttpsUtil.getSslSocketFactory(arrayOf(App.context.resources.openRawResource(R.raw.https_keystore)), null, null)
    return OkHttpClient.Builder()
//            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                addInterceptor(HttpLogInterceptor())
            }.build()
}