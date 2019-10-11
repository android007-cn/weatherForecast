package com.cxyzy.weather

import com.cxyzy.weather.ext.provideOkHttpClient
import com.cxyzy.weather.network.HttpRepository
import com.cxyzy.weather.viewmodels.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpClient() }
    single { HttpRepository }
    viewModel { WeatherViewModel() }
}
