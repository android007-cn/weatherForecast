package com.cxyzy.demo.network

import com.cxyzy.demo.ext.provideOkHttpClient
import com.cxyzy.demo.utils.OkHttpUrl.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseHttpRepository {
    val api: Api by lazy {
        val okHttpClient = provideOkHttpClient()
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(Api::class.java)
    }
}