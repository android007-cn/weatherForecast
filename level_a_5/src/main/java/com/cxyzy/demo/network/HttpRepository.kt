package com.cxyzy.demo.network

import com.cxyzy.demo.utils.OkHttpUrl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpRepository {
    suspend fun getDailyWeather() = api.getDailyWeather(cityName = "南京")

    private val api: Api by lazy {
        val okHttpClient = provideOkHttpClient()
        Retrofit.Builder()
                .baseUrl(OkHttpUrl.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(Api::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .apply {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }.build()
    }
}